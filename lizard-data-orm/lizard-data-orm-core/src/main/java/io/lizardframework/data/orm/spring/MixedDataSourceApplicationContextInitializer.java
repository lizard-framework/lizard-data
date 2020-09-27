package io.lizardframework.data.orm.spring;

import io.lizardframework.data.orm.spring.register.MixedDataSourceBeanRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceApplicationContextInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

	private static final MixedDataSourceBeanRegister MIXED_DATA_SOURCE_BEAN_REGISTER = new MixedDataSourceBeanRegister();

	@Override
	public void initialize(GenericApplicationContext genericApplicationContext) {
		try {
			MIXED_DATA_SOURCE_BEAN_REGISTER.doRegistry(null, genericApplicationContext);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}
}
