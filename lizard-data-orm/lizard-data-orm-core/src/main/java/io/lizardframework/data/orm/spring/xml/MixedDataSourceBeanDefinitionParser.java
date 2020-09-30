package io.lizardframework.data.orm.spring.xml;

import io.lizardframework.data.orm.spring.register.MixedDataSourceBeanRegister;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import io.lizardframework.data.spring.AbstractXMLBeanDefinitionParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanDefinitionParser extends AbstractXMLBeanDefinitionParser {

	private static final MixedDataSourceBeanRegister MIXED_DATA_SOURCE_BEAN_REGISTER = new MixedDataSourceBeanRegister();

	@Override
	public BeanDefinition doParse(Element element, ParserContext parserContext) {
		try {
			BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();
			// get mixed-datasource name
			String mixedName = element.getAttribute("mixed-name");
			if (StringUtils.isNotEmpty(mixedName)) {
				MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean = new MixedDataSourceRegisterMBean();
				mixedDataSourceRegisterMBean.setMixedDataSourceName(mixedName);

				// get mybatis-sqlsession-factory name
				String mybatisSqlsessionFactory = element.getAttribute("mybatis-sqlsession-factory");
				if (StringUtils.isNotEmpty(mybatisSqlsessionFactory)) {
					mixedDataSourceRegisterMBean.setMybatisSqlSessionFactory(mybatisSqlsessionFactory);
				}

				MIXED_DATA_SOURCE_BEAN_REGISTER.doRegistry(mixedDataSourceRegisterMBean, beanDefinitionRegistry);
			}

			return null;
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}
}
