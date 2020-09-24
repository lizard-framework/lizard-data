package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.annotation.RepositorySharding;
import io.lizardframework.data.orm.datasource.DataSourceKey;
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
 * RepositorySharding注解，分库拦截器（优先级高于ReadWriteAnnotationInterceptor）
 *
 * @author xueqi
 * @date 2020-09-16
 */
@Slf4j
public class RepositoryShardingAnnotationInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {

		// 1. 获取实际运行的方法
		Method realMethod = MethodUtils.realMethod(methodInvocation);

		// 2. 获取@RepositorySharding和@Transactional注解
		RepositorySharding rsAnno = MethodUtils.getAnnotation(realMethod, methodInvocation, RepositorySharding.class);
		Transactional      txAnno = MethodUtils.getAnnotation(realMethod, methodInvocation, Transactional.class);

		// 是否需要清理DataSourceStrategy标志位
		boolean needClean = false;
		try {
			// 获取当前线程的DataSourceStrategy
			DataSourceStrategy dataSourceStrategy = StrategyHolder.getDataSourceStrategy();

			// 如果当前线程中没有DataSourceStrategy，表示第一次进入到@RepositorySharding注解的方法
			if (dataSourceStrategy == null) {
				//  todo: shardingkey计算逻辑待完善
				dataSourceStrategy = new DataSourceStrategy(null, "todo:", txAnno != null);
				StrategyHolder.addDataSourceStrategy(dataSourceStrategy);
				needClean = true;
			} else if (dataSourceStrategy.isTransaction()) {
				// 当前线程已经运行在一个事务中,需要根据@Transactional注解判断是否开启新事务(REQUIRES_NEW or NOT_SUPPORTED),开启新事务需要添加新的DataSourceStrategy
				if (txAnno != null &&
						(Propagation.REQUIRES_NEW.equals(txAnno.propagation())
								|| Propagation.NOT_SUPPORTED.equals(txAnno.propagation()))
				) {
					// @RepositorySharding只负责分库，不负责读写
					DataSourceStrategy newStrategy = new DataSourceStrategy(null, "todo:", true);
					StrategyHolder.addDataSourceStrategy(newStrategy);
					needClean = true;
				}

				// 当前线程已经在一个事务中，即使标注@RepositorySharding注解，也不再切换分库数据源
			} else {
				// 当前线程没有运行在事务中，需要添加一个新的DataSourceStrategy,是否有事务与@Transactional注解有关
				DataSourceStrategy newStrategy = new DataSourceStrategy(null, "todo:", txAnno != null);
				StrategyHolder.addDataSourceStrategy(newStrategy);
				needClean = true;
			}

			return methodInvocation.proceed();
		} finally {
			if (needClean) {
				StrategyHolder.removeDataSourceStrategy();
			}
		}
	}
}
