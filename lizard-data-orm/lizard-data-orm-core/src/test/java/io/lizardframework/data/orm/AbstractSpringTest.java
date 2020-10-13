package io.lizardframework.data.orm;

import org.junit.After;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xueqi
 * @date 2020-10-09
 */
public abstract class AbstractSpringTest {
	private ClassPathXmlApplicationContext classPathXmlApplicationContext;

	protected <T> T getBean(String xml, String beanName) {
		classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:" + xml);

		return (T) classPathXmlApplicationContext.getBean(beanName);
	}

	protected <T> T getBean(String beanName) {
		return getBean("applicationContext-test.xml", beanName);
	}

	@After
	public void after() {
		if (classPathXmlApplicationContext != null) {
			classPathXmlApplicationContext.close();
		}
	}
}

