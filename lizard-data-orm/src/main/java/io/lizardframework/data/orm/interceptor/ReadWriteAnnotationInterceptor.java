package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.annotation.ReadWrite;
import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * ReadWrite注解 读写分离拦截器 (优先级高于@Transactional注解)
 * <p>
 * 需要在DATASOURCE_STRATEGY_STACK中添加DataSourceStrategy的场景：
 * 1. txAnno == null: 当前方法没有被@Transactional注解标注，可能是无事务运行或已经在一个嵌套事务中
 * a. DataSourceKey.hasTransactional() == false
 * 2. txAnno != null && propagation = Propagation.REQUIRES_NEW
 * 3. txAnno != null
 * </p>
 *
 * @author xueqi
 * @date 2020-09-15
 * @see io.lizardframework.data.orm.annotation.ReadWrite
 */
@Slf4j
public class ReadWriteAnnotationInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("Enter into ReadWriteAnnotationInterceptor invocation:{}", invocation.toString());

		// 获取实际运行的Method
		Method realMethod = MethodUtils.realMethod(invocation);

		// 获取ReadWrite和Transactional注解
		ReadWrite     readWrite = MethodUtils.getAnnotation(realMethod, invocation, ReadWrite.class);
		Transactional txAnno    = MethodUtils.getAnnotation(realMethod, invocation, Transactional.class);

		// 是否需要清理DataSourceStrategy标志位
		boolean needClean = false;
		try {
			// 获取当前线程的DataSourceStrategy
			DataSourceStrategy dataSourceStrategy = DataSourceKey.getDataSourceStrategy();

			// 如果当前线程中没有DataSourceStrategy，表示第一个进入到只有@ReadWrite注解的方法
			if (dataSourceStrategy == null) {
				dataSourceStrategy = new DataSourceStrategy(readWrite.type(), null, txAnno != null);
				DataSourceKey.addDataSourceStrategy(dataSourceStrategy);
				needClean = true;
			} else if (DataSourceKey.hasTransactional()) {
				// 当前线程已经运行在一个事务中,需要根据@Transactional注解判断是否开启新事务,开启新事务需要添加新的DataSourceStrategy
				if (txAnno != null && Propagation.REQUIRES_NEW.equals(txAnno.propagation())) {
					// RW只负责切换读写数据源，RepositoryShardingKey还是从上一个strategy中获取
					DataSourceStrategy newStrategy = new DataSourceStrategy(readWrite.type(), dataSourceStrategy.getRepositoryShardingKey(), true);
					DataSourceKey.addDataSourceStrategy(newStrategy);
					needClean = true;
				} else {
					// hasTransactional()==true可能是RepositorySharding拦截器设置的，此时需要判断dataSourceStrategy中的rw type是否存在，如果不存在则添加新的DataSourceStrategy
					if (dataSourceStrategy.getReadWriteType() == null) {
						DataSourceStrategy newStrategy = new DataSourceStrategy(readWrite.type(), dataSourceStrategy.getRepositoryShardingKey(), true);
						DataSourceKey.addDataSourceStrategy(newStrategy);
						needClean = true;
					}
				}
			} else {
				// 当前线程没有运行在事务中，需要添加一个新的DataSourceStrategy,是否有事务与@Transactional注解有关
				DataSourceStrategy newStrategy = new DataSourceStrategy(readWrite.type(), dataSourceStrategy.getRepositoryShardingKey(), txAnno != null);
				DataSourceKey.addDataSourceStrategy(newStrategy);
				needClean = true;
			}

			return invocation.proceed();
		} finally {
			if (needClean) {
				DataSourceKey.removeDataSourceStrategy();
			}
		}
	}

}
