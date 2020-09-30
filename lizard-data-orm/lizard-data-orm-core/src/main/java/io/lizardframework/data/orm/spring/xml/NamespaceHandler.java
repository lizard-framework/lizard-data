package io.lizardframework.data.orm.spring.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * lizard-data-orm spring label handler
 *
 * @author xueqi
 * @date 2020-09-22
 */
public class NamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		super.registerBeanDefinitionParser("mixed-datasource", new MixedDataSourceBeanDefinitionParser());
	}
}
