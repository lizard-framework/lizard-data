package io.lizardframework.data.orm;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author xueqi
 * @date 2020-09-23
 */
public class XmlApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext
				= new ClassPathXmlApplicationContext("classpath:applicationContext-test.xml");

		System.out.println("....");
	}

}
