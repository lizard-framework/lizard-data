package io.lizardframework.data.remoting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.lizardframework.data.enums.MixedType;
import io.lizardframework.data.utils.EnvUtils;
import io.lizardframework.data.utils.NetUtils;
import io.lizardframework.data.utils.PropertiesUtils;
import io.lizardframework.data.utils.VersionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-26
 */
@Slf4j
public class MixedConfigFetcher {
	private static final String CFG_PROPERTIES_FILE        = "lizard-data-cfg.properties";
	private static final String API_URL_KEY_PREFIX         = "api.url.";
	private static final String API_GET_MIXED_CONFIG_ORM   = "api.getMixedConfig.orm";
	private static final String API_GET_MIXED_CONFIG_CACHE = "api.getMixedConfig.cache";

	private static final String SUCCESS_RESP_CODE = "000000";

	public static String getMixedConfig(String mixedName, MixedType mixedType) throws Exception {
		log.info("Get mixed config, mixedName:{}, type:{}", mixedName, mixedType);

		// get url config
		String url = getApiUrl(mixedType);
		log.info("Get mixed config url:{}", url);

		// build request param
		Map<String, String> params = new HashMap<>();
		params.put("application", EnvUtils.getApplicationName());
		params.put("hostname", NetUtils.getHostName());
		params.put("mixedName", mixedName);
		params.put("sdkVersion", VersionUtils.getLizardDataVersion());

		// do post request
		String response = NetUtils.post(params, url);

		// check response code
		JsonObject respJsonObj = JsonParser.parseString(response).getAsJsonObject();
		String     respCode    = respJsonObj.get("code").getAsString();
		if (!StringUtils.equals(SUCCESS_RESP_CODE, respCode)) {
			String message = respJsonObj.get("message").getAsString();
			log.warn("Get mixed config fail. response_code:{}, response_message:{}", respCode, message);

			throw new ContextedRuntimeException("Get mixed:" + mixedName + ", type:" + mixedType + ", from url:" + url + " fail.");
		} else {
			String data = respJsonObj.get("data").getAsString();
			return data;
		}
	}

	/**
	 * get url
	 *
	 * @param mixedType
	 * @return
	 */
	public static String getApiUrl(MixedType mixedType) throws Exception {
		try {
			// get active profiles
			String profiles = EnvUtils.getProfilesName();
			String urlKey   = API_URL_KEY_PREFIX + profiles;

			// get url
			String url = PropertiesUtils.getProperty(CFG_PROPERTIES_FILE, urlKey);
			if (MixedType.ORM.equals(mixedType)) {
				url = url + PropertiesUtils.getProperty(CFG_PROPERTIES_FILE, API_GET_MIXED_CONFIG_ORM);
			} else if (MixedType.CACHE.equals(mixedType)) {
				url = url + PropertiesUtils.getProperty(CFG_PROPERTIES_FILE, API_GET_MIXED_CONFIG_CACHE);
			} else {
				throw new IllegalArgumentException("MixedType is error.(" + mixedType + ")");
			}

			return url;
		} catch (Exception e) {
			log.warn("Get mixed config api url error.", e);
			throw e;
		}
	}

}
