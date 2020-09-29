package io.lizardframework.data.remoting.impl;

import io.lizardframework.data.remoting.Fetcher;
import io.lizardframework.data.utils.NetUtils;
import io.lizardframework.data.utils.PropertiesUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@Slf4j
public class SecurityFetcher extends Fetcher {
	private static final String API_DECRYPT = "api.security.decrypt";

	private SecurityFetcher() {
	}

	private static class InstanceHolder {
		private static SecurityFetcher INSTANCE = new SecurityFetcher();
	}

	public static SecurityFetcher getInstance() {
		return SecurityFetcher.InstanceHolder.INSTANCE;
	}

	public String decrypt(String text) throws Exception {
		// get url
		String url = getUrl() + PropertiesUtils.getProperty(CFG_PROPERTIES_FILE, API_DECRYPT);
		log.info("Decrypt url:{}", url);

		// build request params
		Map<String, String> params = new HashMap<>();
		params.put("text", text);

		// do post request
		String response = NetUtils.post(params, url);
		if (StringUtils.isEmpty(response)) {
			log.warn("Decrypt password from url: " + url + " ,response is null. return original value.");
			return text;
		}

		return getRespData(response);
	}

}
