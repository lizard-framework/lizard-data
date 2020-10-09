package io.lizardframework.data.orm.spring;

import io.lizardframework.data.orm.Constants;
import io.lizardframework.data.orm.spring.register.MixedDataSourceBeanRegister;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import io.lizardframework.data.utils.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;

/**
 * Spring ApplicationContextInitializer expand
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Order(Integer.MAX_VALUE)
@Slf4j
public class MixedDataSourceApplicationContextInitializer implements ApplicationContextInitializer<GenericApplicationContext>, Constants {

	private static final MixedDataSourceBeanRegister MIXED_DATA_SOURCE_BEAN_REGISTER = new MixedDataSourceBeanRegister();

	@Override
	public void initialize(GenericApplicationContext genericApplicationContext) {
		try {
			// get mixed datasource name list, spring context environment exist MIXED_DATA_NAMES_KEY
			String names = EnvUtils.getEnvValue(null, null, MIXED_DATA_NAMES_KEY);
			if (StringUtils.isNotEmpty(names)) {

				String[] mixedNames = StringUtils.split(names, COMMA);
				if (ArrayUtils.isNotEmpty(mixedNames)) {
					for (String mixedName : mixedNames) {
						MixedDataSourceRegisterMBean mBean = new MixedDataSourceRegisterMBean();
						mBean.setMixedDataSourceName(mixedName);

						// get mybatisSqlSessionFactory config value
						String mybatisSqlSessionFactoryKey = String.format(MIXED_DATA_mybatisSqlSessionFactory_KEY_FORMAT, mixedName);
						String mybatisSqlSessionFactory    = EnvUtils.getEnvValue(null, null, mybatisSqlSessionFactoryKey);
						if (StringUtils.isNotEmpty(mybatisSqlSessionFactory)) {
							mBean.setMybatisSqlSessionFactory(mybatisSqlSessionFactory);
						}

						// registry mixed datasource bean
						MIXED_DATA_SOURCE_BEAN_REGISTER.doRegistry(mBean, genericApplicationContext);
					}
				}

			} else {
				log.debug("Not Found {} value in context environment. don't initialize Mixed DataSource.", MIXED_DATA_NAMES_KEY);
			}
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}
}
