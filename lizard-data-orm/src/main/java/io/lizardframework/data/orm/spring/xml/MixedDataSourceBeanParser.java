package io.lizardframework.data.orm.spring.xml;

import io.lizardframework.data.orm.spring.register.MixedDataSourceBeanRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanParser extends AbstractSingleBeanDefinitionParser {

	private static final MixedDataSourceBeanRegister MIXED_DATA_SOURCE_BEAN_REGISTER = new MixedDataSourceBeanRegister();

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
		MIXED_DATA_SOURCE_BEAN_REGISTER.doRegistry(beanDefinitionRegistry);
	}
}
