package io.lizardframework.data.utils;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author xueqi
 * @date 2020-09-15
 */
public class MethodUtils {

	/**
	 * 获取实际运行方法
	 *
	 * @param invocation
	 * @return
	 */
	public static Method realMethod(MethodInvocation invocation) {
		Class<?> targetClazz = AopUtils.getTargetClass(invocation);
		// 获得最匹配的一个可以执行的方法
		Method realMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClazz);
		// 判断是否为桥接方法：即子类重写父类的方法，且参数或者返回值不一致（此时，我们需要获取子类真正的方法）
		realMethod = BridgeMethodResolver.findBridgedMethod(realMethod);

		return realMethod;
	}

	/**
	 * 获取方法注解
	 *
	 * @param realMethod
	 * @param invocation
	 * @param annotation
	 * @param <T>
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Method realMethod, MethodInvocation invocation, Class<T> annotation) {
		// 尝试获取方法级别的注解
		T anno = realMethod.getAnnotation(annotation);
		if (anno == null) {
			// 尝试获取类级别的注解
			anno = invocation.getThis().getClass().getAnnotation(annotation);
		}

		return anno;
	}
}
