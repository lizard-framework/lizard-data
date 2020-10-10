package io.lizardframework.data.utils;

import org.aopalliance.intercept.MethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author xueqi
 * @date 2020-09-23
 */
public class BeanUtils {

	/**
	 * build bean name, use '-' spearator
	 *
	 * @param params
	 * @return
	 */
	public static String genBeanName(Object... params) {
		return StringUtils.joinWith("-", params);
	}

	/**
	 * registry spring bean
	 *
	 * @param beanName
	 * @param beanDefinitionRegistry
	 * @param clazz
	 * @param scope
	 * @param propertyValues
	 */
	public static void registryBean(String beanName, BeanDefinitionRegistry beanDefinitionRegistry, Class clazz,
	                                BeanDefinitionDsl.Scope scope, List<PropertyValue> propertyValues) {

		RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(clazz);
		rootBeanDefinition.setScope(scope.toString().toLowerCase());
		if (!CollectionUtils.isEmpty(propertyValues)) {
			for (PropertyValue propertyValue : propertyValues) {
				rootBeanDefinition.getPropertyValues().addPropertyValue(propertyValue);
			}
		}
		beanDefinitionRegistry.registerBeanDefinition(beanName, rootBeanDefinition);
	}

	/**
	 * registry singleton bean with propertyValues
	 *
	 * @param beanName
	 * @param beanDefinitionRegistry
	 * @param clazz
	 * @param propertyValues
	 */
	public static void registryBean(String beanName, BeanDefinitionRegistry beanDefinitionRegistry, Class clazz,
	                                List<PropertyValue> propertyValues) {
		registryBean(beanName, beanDefinitionRegistry, clazz, BeanDefinitionDsl.Scope.SINGLETON, propertyValues);
	}

	/**
	 * registry singleton bean without propertyValues
	 *
	 * @param beanName
	 * @param beanDefinitionRegistry
	 * @param clazz
	 */
	public static void registryBean(String beanName, BeanDefinitionRegistry beanDefinitionRegistry, Class clazz) {
		registryBean(beanName, beanDefinitionRegistry, clazz, BeanDefinitionDsl.Scope.SINGLETON, null);
	}

	/**
	 * registry AspectJExpressionPointcut Advisor
	 *
	 * @param beanName
	 * @param beanDefinitionRegistry
	 * @param expression
	 * @param interceptor
	 * @param order
	 */
	public static void registryPointcutAdvisorBean(String beanName, BeanDefinitionRegistry beanDefinitionRegistry,
	                                               String expression, MethodInterceptor interceptor, int order) {
		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression(expression);

		registryBean(beanName, beanDefinitionRegistry, DefaultPointcutAdvisor.class, Arrays.asList(
				new PropertyValue("pointcut", pointcut),
				new PropertyValue("advice", interceptor),
				new PropertyValue("order", order)
		));
	}
}
