package io.lizardframework.data.orm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xueqi
 * @date 2020-10-09
 */
public abstract class AbstractSpringTest {

	protected <T> T getBean(String xml, String beanName) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext
				= new ClassPathXmlApplicationContext("classpath:" + xml);

		return (T) classPathXmlApplicationContext.getBean(beanName);
	}

}
