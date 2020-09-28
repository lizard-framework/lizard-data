package io.lizardframework.data.orm.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author xueqi
 * @date 2020-09-28
 */
@Slf4j
public class TableShardingAnnotationInterceptor implements MethodInterceptor, ApplicationContextAware {
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

	}
}
