package io.lizardframework.data.spring;

import io.lizardframework.data.utils.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;

/**
 * @author xueqi
 * @date 2020-09-30
 */
@Order(100) // order must be more than lizard-data and lizard-cache
@Slf4j
public class LizardDataFrameworkInitializer implements ApplicationContextInitializer<GenericApplicationContext> {

	@Override
	public void initialize(GenericApplicationContext genericApplicationContext) {
		log.info("Begin initialize Lizard-Data Framework");

		// init about environment utils
		LizardDataFrameworkInitializer.doEnvironmentInitialize(genericApplicationContext.getEnvironment());
	}

	public static void doEnvironmentInitialize(Environment environment) {
		EnvUtils.initEnvironment(environment);
	}
}
