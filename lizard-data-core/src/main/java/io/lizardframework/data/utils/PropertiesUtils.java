package io.lizardframework.data.utils;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xueqi
 * @date 2020-09-26
 */
public class PropertiesUtils {

	private static final Map<String, Properties> PROPERTIES_MAP = new ConcurrentHashMap<>();

	public static String getProperty(String propertieFile, String key) throws IOException {
		Properties properties = PROPERTIES_MAP.get(propertieFile);
		if (properties == null) {
			synchronized (PropertiesUtils.class) {
				properties = PROPERTIES_MAP.get(propertieFile);
				if (properties == null) {
					properties = PropertiesLoaderUtils.loadAllProperties(propertieFile);
					PROPERTIES_MAP.put(propertieFile, properties);
				}
			}
		}

		return properties.getProperty(key);
	}

}
