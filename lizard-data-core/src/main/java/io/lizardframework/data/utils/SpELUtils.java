package io.lizardframework.data.utils;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.Assert;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public class SpELUtils {

	/**
	 * calculation spel value
	 *
	 * @param args
	 * @param paramNames
	 * @param expression
	 * @param beanFactory
	 * @return
	 */
	public static Object calculation(Object[] args, String[] paramNames, String expression, BeanFactory beanFactory) {
		Assert.hasText(expression, "expression must be text");

		ExpressionParser          expressionParser = new SpelExpressionParser();
		StandardEvaluationContext context          = new StandardEvaluationContext();
		if (beanFactory != null) {
			context.setBeanResolver(new BeanFactoryResolver(beanFactory));
		}

		if (ArrayUtils.isNotEmpty(args) && ArrayUtils.isNotEmpty(paramNames)) {
			// check args nums equals params name nums
			if (args.length != paramNames.length) {
				throw new IllegalArgumentException("args length must be equal to paraNames length");
			}

			for (int i = 0; i < args.length; i++) {
				context.setVariable(paramNames[i], args[i]);
			}
		}

		return expressionParser.parseExpression(expression).getValue(context);
	}

}
