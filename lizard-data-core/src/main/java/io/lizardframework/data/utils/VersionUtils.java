package io.lizardframework.data.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * lizard-data version utils
 *
 * @author xueqi
 * @date 2020-09-06
 */
@Slf4j
public class VersionUtils {

	public static String getLizardDataVersion() {
		// default version is unknown
		String version = "unkonwn";

		// get jar file path
		String path = null;
		try {
			path = VersionUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			// split lizard-data to .jar
			if (StringUtils.isNotEmpty(path) && StringUtils.contains(path, "lizard-data")) {
				version = StringUtils.substring(path, path.lastIndexOf("lizard-data-") + 12, path.lastIndexOf(".jar"));
			}
		} catch (Exception e) {
			log.error("Can not parse lizard-data version from path:{}", path, e);
		}

		return version;
	}

}
