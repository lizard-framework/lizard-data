package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.annotation.ReadWrite;
import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.utils.MethodUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * ReadWrite注解 读写分离拦截器 (优先级高于@Transactional注解)
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

		// 1. 判断是否需要切换读写数据源:
		// 当前方法线程没有运行在事务中
		// 当前方法运行在事务中&&repository_sharding_key存在&&datasource_key不存在(表示已完成分库策略但是第一次处理读写逻辑)
		// 事务隔离级别为REQUIRED_NEW级别

		ReadWrite     readWrite = MethodUtils.getAnnotation(realMethod, invocation, ReadWrite.class);
		Transactional txAnno    = MethodUtils.getAnnotation(realMethod, invocation, Transactional.class);
		boolean       needClean = false;

		// 当前方法没有被@Transactional注解标注，可能是无事务运行或已经在一个嵌套事务中
		if (txAnno == null) {
			// 当前方法线程没有运行在事务中，需要将DataSourceStrategy加入当前线程ThreadLocal中
			if (!DataSourceKey.hasTransactional()) {
				DataSourceStrategy strategy = new DataSourceStrategy();
				strategy.setReadWriteType(readWrite.type());
				strategy.setRepositoryShardingKey(DataSourceKey.getRepositoryShardingKey());
				strategy.setDataSourceKey(DataSourceKey.getCurrentDataSourceKey());
				strategy.setTransaction(false);
				DataSourceKey.addDataSourceStrategy(strategy);
				needClean = true;
			}
			// 当前方法已经运行在一个事务中，不再切换读写数据源
		} else {

		}

		return invocation.proceed();
	}
}
