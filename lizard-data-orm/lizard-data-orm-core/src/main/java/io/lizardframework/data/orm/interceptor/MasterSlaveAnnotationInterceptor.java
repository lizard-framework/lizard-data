package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.annotation.MasterSlave;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * ReadWrite注解 读写分离拦截器 (优先级高于@Transactional注解)
 *
 * @author xueqi
 * @date 2020-09-15
 * @see MasterSlave
 */
@Slf4j
public class MasterSlaveAnnotationInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		log.debug("Enter into ReadWriteAnnotationInterceptor invocation:{}", invocation.toString());

		// 获取实际运行的Method
		Method realMethod = MethodUtils.realMethod(invocation);

		// 获取ReadWrite和Transactional注解
		MasterSlave   masterSlave = MethodUtils.getAnnotation(realMethod, invocation, MasterSlave.class);
		Transactional txAnno      = MethodUtils.getAnnotation(realMethod, invocation, Transactional.class);

		// 是否需要清理DataSourceStrategy标志位
		boolean needClean = false;
		try {
			// 获取当前线程的DataSourceStrategy
			DataSourceStrategy dataSourceStrategy = StrategyHolder.getDataSourceStrategy();

			// 如果当前线程中没有DataSourceStrategy，表示第一次进入@ReadWrite注解的方法
			if (dataSourceStrategy == null) {
				dataSourceStrategy = new DataSourceStrategy(masterSlave.type(), null, txAnno != null);
				StrategyHolder.addDataSourceStrategy(dataSourceStrategy);
				needClean = true;
			} else if (StrategyHolder.hasTransactional()) {
				// 当前线程已经运行在一个事务中,需要根据@Transactional注解判断是否开启新事务,开启新事务需要添加新的DataSourceStrategy
				if (txAnno != null &&
						(Propagation.REQUIRES_NEW.equals(txAnno.propagation())
								|| Propagation.NOT_SUPPORTED.equals(txAnno.propagation()))
				) {
					// @ReadWrite只负责切换读写数据源，新的DataSourceStrategy不从上一个策略中获取分库key
					DataSourceStrategy newStrategy = new DataSourceStrategy(masterSlave.type(), null, true);
					StrategyHolder.addDataSourceStrategy(newStrategy);
					needClean = true;
				} else if (dataSourceStrategy.getMasterSlaveType() == null) {
					// hasTransactional()==true可能是RepositorySharding拦截器设置的，此时需要判断dataSourceStrategy中的rw type是否存在，如果不存在则添加新的DataSourceStrategy
					DataSourceStrategy newStrategy = new DataSourceStrategy(masterSlave.type(), dataSourceStrategy.getRepositoryShardingKey(), true);
					StrategyHolder.addDataSourceStrategy(newStrategy);
					needClean = true;
				}
			} else {
				// 当前线程没有运行在事务中，需要添加一个新的DataSourceStrategy,是否有事务与@Transactional注解有关
				DataSourceStrategy newStrategy = new DataSourceStrategy(masterSlave.type(), dataSourceStrategy.getRepositoryShardingKey(), txAnno != null);
				StrategyHolder.addDataSourceStrategy(newStrategy);
				needClean = true;
			}

			return invocation.proceed();
		} finally {
			if (needClean) {
				StrategyHolder.removeDataSourceStrategy();
			}
		}
	}

}