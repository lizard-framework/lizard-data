package io.lizardframework.data.remoting;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.lizardframework.data.utils.EnvUtils;
import io.lizardframework.data.utils.PropertiesUtils;

/**
 * @author xueqi
 * @date 2020-09-27
 */
public abstract class Fetcher {
	public static final String CFG_PROPERTIES_FILE = "lizard-data-cfg.properties";
	public static final String API_URL_KEY_PREFIX  = "api.url.";
	public static final String SUCCESS_RESP_CODE   = "000000";


	protected String getUrl() throws Exception {
		// get active profiles
		String profiles = EnvUtils.getProfilesName();
		String urlKey   = API_URL_KEY_PREFIX + profiles;

		return PropertiesUtils.getProperty(CFG_PROPERTIES_FILE, urlKey);
	}

	protected String getRespCode(String response) {
		JsonObject respJsonObj = JsonParser.parseString(response).getAsJsonObject();

		String respCode = respJsonObj.get("code").getAsString();

		return respCode;
	}

	protected String getRespMessage(String response) {
		JsonObject respJsonObj = JsonParser.parseString(response).getAsJsonObject();

		String respCode = respJsonObj.get("message").getAsString();

		return respCode;
	}

	protected String getRespData(String response) {
		JsonObject respJsonObj = JsonParser.parseString(response).getAsJsonObject();

		JsonElement data = respJsonObj.get("data");
		if (data instanceof JsonObject || data instanceof JsonArray) {
			return data.toString();
		} else {
			return data.getAsString();
		}
	}
}
