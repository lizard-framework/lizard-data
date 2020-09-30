package io.lizardframework.data.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.env.Environment;
import org.w3c.dom.Element;

/**
 * @author xueqi
 * @date 2020-09-30
 */
public abstract class AbstractXMLBeanDefinitionParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		this.doInit(parserContext.getReaderContext().getEnvironment());

		return doParse(element,parserContext);
	}

	/**
	 * do lizard context init
	 *
	 * @param environment
	 */
	private void doInit(Environment environment) {
		LizardDataFrameworkInitializer.doEnvironmentInitialize(environment);
	}

	protected abstract BeanDefinition doParse(Element element, ParserContext parserContext);
}
