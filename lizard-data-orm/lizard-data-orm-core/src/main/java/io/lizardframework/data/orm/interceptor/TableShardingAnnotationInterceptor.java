package io.lizardframework.data.orm.interceptor;

import io.lizardframework.data.orm.Constants;
import io.lizardframework.data.orm.annotation.TableSharding;
import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import io.lizardframework.data.utils.MethodUtils;
import io.lizardframework.data.utils.SpELUtils;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * table sharding interceptor
 *
 * @author xueqi
 * @date 2020-09-28
 */
@Slf4j
public class TableShardingAnnotationInterceptor implements MethodInterceptor, ApplicationContextAware, Constants {
	private ApplicationContext      applicationContext;
	private ParameterNameDiscoverer paraNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		log.debug("Enter into TableShardingAnnotationInterceptor invocation:{}", methodInvocation.toString());

		boolean needClean = false;
		try {
			// get real run method
			Method realMethod = MethodUtils.realMethod(methodInvocation);

			TableSharding tsAnno = MethodUtils.getAnnotation(realMethod, methodInvocation, TableSharding.class);

			// calc table sharding strategy
			Map<String, String> strategy = getTableShardingStrategy(tsAnno, methodInvocation, realMethod);
			StrategyHolder.addTableShardingStrategy(strategy);
			needClean = true;

			return methodInvocation.proceed();
		} finally {
			if (needClean) {
				StrategyHolder.removeTableShardingStrategy();
			}
		}

	}

	private Map<String, String> getTableShardingStrategy(TableSharding tsAnno, MethodInvocation methodInvocation, Method realMethod) {
		String[] paramsName;

		// check org.apache.ibatis.annotations.Param if exist
		if (ClassUtils.isPresent(MYBATIS_Param_ANNOTATION_CLASS, null)) {
			// because mybatis dao proxy args name is: args0,args1...
			List<String>   paramNameList         = new ArrayList<>();
			Annotation[][] annotationDyadicArray = realMethod.getParameterAnnotations();

			if (ArrayUtils.isNotEmpty(annotationDyadicArray)) {
				for (Annotation[] annotations : annotationDyadicArray) {
					if (ArrayUtils.isNotEmpty(annotations)) {
						for (Annotation annotation : annotations) {
							if (annotation instanceof Param) {
								paramNameList.add(((Param) annotation).value());
							}
						}
					}
				}
			}

			paramsName = new String[paramNameList.size()];
			paramNameList.toArray(paramsName);
		} else {
			paramsName = paraNameDiscoverer.getParameterNames(realMethod);
		}

		Object value = SpELUtils.calculation(methodInvocation.getArguments(), paramsName, tsAnno.strategy(), applicationContext);
		if (value == null) {
			throw new ContextedRuntimeException("Table sharding strategy spel calculation result is null. strategy: " + tsAnno.strategy());
		}

		return (Map<String, String>) value;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
