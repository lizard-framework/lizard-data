package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.annotation.RepositorySharding;
import io.lizardframework.data.orm.datasource.strategy.DataSourceStrategy;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.orm.support.utils.TransactionalUtil;
import io.lizardframework.data.utils.MethodUtils;
import io.lizardframework.data.utils.SpELUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

/**
 * RepositorySharding注解，分库拦截器（优先级高于ReadWriteAnnotationInterceptor）
 *
 * @author xueqi
 * @date 2020-09-16
 */
@Slf4j
public class RepositoryShardingAnnotationInterceptor implements MethodInterceptor, ApplicationContextAware {
	private ApplicationContext      applicationContext;
	private ParameterNameDiscoverer paraNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		String invocationInfo = invocation.toString();
		log.debug("Enter into RepositoryShardingAnnotationInterceptor, invocation: '{}'", invocationInfo);

		// 1. 获取实际运行的方法
		Method realMethod = MethodUtils.realMethod(invocation);

		// 2. 获取@RepositorySharding和@Transactional注解
		RepositorySharding rsAnno = MethodUtils.getAnnotation(realMethod, invocation, RepositorySharding.class);
		Transactional      txAnno = MethodUtils.getAnnotation(realMethod, invocation, Transactional.class);

		// 是否需要清理DataSourceStrategy标志位
		boolean needClean = false;
		try {
			// 获取当前线程的DataSourceStrategy
			DataSourceStrategy dataSourceStrategy = StrategyHolder.getDataSourceStrategy();

			// 如果当前线程中没有DataSourceStrategy，表示第一次进入到@RepositorySharding注解的方法
			if (dataSourceStrategy == null) {

				dataSourceStrategy = new DataSourceStrategy(null,
						getShardingkey(rsAnno, invocation, realMethod),
						TransactionalUtil.hasTx(txAnno, false));
				log.debug("Adding new datasource strategy, because currenct thread datasource strategy stack is null. strategy: '{}'", dataSourceStrategy);

				StrategyHolder.addDataSourceStrategy(dataSourceStrategy);
				needClean = true;
			} else if (dataSourceStrategy.isTransaction()) {
				// 当前线程已经运行在一个事务中,需要根据@Transactional注解判断是否允许切换连接,添加新的DataSourceStrategy
				if (TransactionalUtil.allowChangeConnection(txAnno)) {

					// @RepositorySharding只负责分库，不负责读写
					DataSourceStrategy newStrategy = new DataSourceStrategy(null,
							getShardingkey(rsAnno, invocation, realMethod),
							TransactionalUtil.hasTx(txAnno, true));
					log.debug("Adding new datasource strategy, because transaction propagation is: '{}'. strategy: '{}'", txAnno.propagation(), newStrategy);

					StrategyHolder.addDataSourceStrategy(newStrategy);
					needClean = true;
				} else {
					// 当前线程已经在一个事务中，即使标注@RepositorySharding注解，也不再切换分库数据源
					log.debug("Needn't add new datasource strategy, because current transaction has assigned strategy: '{}'", dataSourceStrategy);
				}
			} else {

				// 当前线程没有运行在事务中，需要添加一个新的DataSourceStrategy,是否有事务与@Transactional注解有关
				DataSourceStrategy newStrategy = new DataSourceStrategy(null,
						getShardingkey(rsAnno, invocation, realMethod),
						TransactionalUtil.hasTx(txAnno, false));
				log.debug("Adding new new datasource strategy, because no run in transaction. strategy: '{}'", newStrategy);

				StrategyHolder.addDataSourceStrategy(newStrategy);
				needClean = true;
			}

			return invocation.proceed();
		} finally {
			if (needClean) {
				log.debug("Cleaning Repository Sharding DataSource strategy, invocation: '{}'", invocation);
				StrategyHolder.removeDataSourceStrategy();
			}
		}
	}

	private String getShardingkey(RepositorySharding rsAnno, MethodInvocation methodInvocation, Method realMethod) {
		Object[] args       = methodInvocation.getArguments();
		String[] paramsName = paraNameDiscoverer.getParameterNames(realMethod);

		Object value = SpELUtils.calculation(args, paramsName, rsAnno.strategy(), applicationContext);
		if (value == null) {
			throw new ContextedRuntimeException("Repository sharding strategy spel calculation result is null. strategy: " + rsAnno.strategy());
		}

		return value.toString();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
