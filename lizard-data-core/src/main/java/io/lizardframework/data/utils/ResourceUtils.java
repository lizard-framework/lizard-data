package io.lizardframework.data.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * get classpath resource file
 *
 * @author xueqi
 * @date 2020-09-07
 */
public class ResourceUtils {

	public static Properties getProperties(String resource) {
		Properties prop = new Properties();

		InputStream is = null;
		try {
			is = ResourceUtils.class.getClassLoader().getResourceAsStream(resource);
			if (is != null) {
				prop.load(is);
			}
		} catch (IOException e) {
			// do nothing
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}

		return prop;
	}

}
