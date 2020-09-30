package io.lizardframework.data.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

/**
 * get env value and cuurent env name utils
 * <p>
 * order: system env --> jvm ---> spring
 *
 * @author xueqi
 * @date 2020-09-06
 */
public class EnvUtils {
	private static Environment environment;

	/**
	 * Spring Environment init
	 *
	 * @param environment
	 */
	public static void initEnvironment(Environment environment) {
		if (EnvUtils.environment == null)
			EnvUtils.environment = environment;
	}

	/**
	 * get application profile active name
	 *
	 * @return if profiles is null ,return default
	 */
	public static String getProfilesName() {
		String[] activeProfiles = environment.getActiveProfiles();
		if (activeProfiles == null || activeProfiles.length == 0) return "default";

		return activeProfiles[0];
	}

	/**
	 * get application name. use spring.application.name
	 *
	 * @return
	 */
	public static String getApplicationName() {
		return environment.getProperty("spring.application.name");
	}

	/**
	 * get string env value
	 *
	 * @param systemProperty
	 * @param jvmProperty
	 * @param springProperty
	 * @return
	 */
	public static String getEnvValue(String systemProperty, String jvmProperty, String springProperty) {
		String value = null;

		// get value from system env
		if (StringUtils.isNotEmpty(systemProperty)) {
			value = System.getenv(systemProperty);
		}

		// get value from jvm command property (-Dxxx)
		if (value == null) {
			if (StringUtils.isNotEmpty(jvmProperty)) {
				value = System.getProperty(jvmProperty);
			}
		}

		// get value from spring context properties
		if (value == null) {
			if (StringUtils.isNotEmpty(springProperty)) {
				value = environment.getProperty(springProperty);
			}
		}

		return value;
	}
}
