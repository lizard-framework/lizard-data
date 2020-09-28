package io.lizardframework.data.orm.spring.xml;

import io.lizardframework.data.orm.spring.register.MixedDataSourceBeanRegister;
import io.lizardframework.data.orm.spring.register.meta.DataSourceRegisterMBean;
import io.lizardframework.data.utils.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.w3c.dom.Element;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanParser implements BeanDefinitionParser {

	private static final MixedDataSourceBeanRegister MIXED_DATA_SOURCE_BEAN_REGISTER = new MixedDataSourceBeanRegister();

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		try {
			doInit(parserContext.getReaderContext().getEnvironment());

			BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
			// get mixed-datasource name list
			String mixedNames = element.getAttribute("mixed-name");
			if (StringUtils.isNotEmpty(mixedNames)) {
				String[] mixedNameList = StringUtils.split(mixedNames, ",");
				for (String mixedName : mixedNameList) {
					DataSourceRegisterMBean dataSourceRegisterMBean = new DataSourceRegisterMBean();
					dataSourceRegisterMBean.setMixedDataSourceName(mixedName);
					MIXED_DATA_SOURCE_BEAN_REGISTER.doRegistry(dataSourceRegisterMBean, beanDefinitionRegistry);
				}
			}

			return null;
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}

	/**
	 * do lizard context init
	 *
	 * @param environment
	 */
	private void doInit(Environment environment) {
		EnvUtils.initEnvironment(environment);
	}
}
