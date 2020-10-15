package io.lizardframework.data;

import org.apache.commons.lang3.SystemUtils;

/**
 * @author xueqi
 * @date 2020-10-09
 */
public interface CommonConstants {

	String COMMA = ",";
	String UTF_8 = "utf-8";

	String PROJECT = "lizard-data";

	// ------- local bak file ------- //
	String USER_DIR_HOME       = SystemUtils.getUserHome().getAbsolutePath();
	String LOCAL_BAK_FILE_PATH = USER_DIR_HOME + "/" + PROJECT + "/conf/";
	String LOCAL_BAK_FILE_EXT  = ".bak";
}
