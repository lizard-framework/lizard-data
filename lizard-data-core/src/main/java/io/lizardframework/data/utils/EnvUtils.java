package io.lizardframework.data.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * get env value and cuurent env name utils
 * <p>
 * order: system env --> jvm ---> spring
 *
 * @author xueqi
 * @date 2020-09-06
 */
public class EnvUtils {
	// spring properties value mapper
	private static Map<String, Object> springPropertiesMapper = new ConcurrentHashMap<>();

	/**
	 * spring context init hook
	 *
	 * @param properties
	 */
	public static void initHook(Map<String, Object> properties) {
		if (properties != null && properties.size() > 0) {
			springPropertiesMapper.putAll(properties);
		}
	}

	/**
	 * gey application profile active name
	 *
	 * @return if profiles is null ,return default
	 */
	public static String getProfilesName() {
		String profile = getEnvValue("SPRING_PROFILES_ACTIVE", "spring.profiles.active", "spring.profiles.active");
		return StringUtils.isNoneEmpty(profile) ? profile : "default";
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
				value = springPropertiesMapper.get(springProperty) == null ? null : springPropertiesMapper.get(springProperty).toString();
			}
		}

		return value;
	}
}
