package io.lizardframework.data.remoting;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.lizardframework.data.enums.MixedType;
import io.lizardframework.data.utils.EnvUtils;
import io.lizardframework.data.utils.NetUtils;
import io.lizardframework.data.utils.PropertiesUtils;
import io.lizardframework.data.utils.VersionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-26
 */
@Slf4j
public class MixedConfigFetcher extends Fetcher {
	private static final String API_GET_MIXED_CONFIG_ORM   = "api.getMixedConfig.orm";
	private static final String API_GET_MIXED_CONFIG_CACHE = "api.getMixedConfig.cache";

	private MixedConfigFetcher() {
	}

	private static class InstanceHolder {
		private static MixedConfigFetcher INSTANCE = new MixedConfigFetcher();
	}

	public static MixedConfigFetcher getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public String getMixedConfig(String mixedName, MixedType mixedType) throws Exception {
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

		// if response is null, try read config from local
		if (StringUtils.isEmpty(response)) {
			String data = loadBakFile(mixedName, mixedType);
			if (StringUtils.isEmpty(data)) {
				throw new ContextedRuntimeException("Get mixed config from url:" + url + " response is null. try get from local is null." +
						" mixed:" + mixedName + ", type:" + mixedType);
			}
			return data;
		}

		// check response code
		String respCode = getRespCode(response);
		if (!StringUtils.equals(SUCCESS_RESP_CODE, respCode)) {
			String message = getRespMessage(response);
			log.warn("Get mixed config fail. response_code:{}, response_message:{}", respCode, message);

			throw new ContextedRuntimeException("Get mixed:" + mixedName + ", type:" + mixedType + ", from url:" + url + " fail.");
		} else {
			String data = getRespData(response);
			saveBakFile(mixedName, mixedType, data);
			return data;
		}
	}

	/**
	 * get url
	 *
	 * @param mixedType
	 * @return
	 */
	private String getApiUrl(MixedType mixedType) throws Exception {
		try {
			// get url
			String url = getUrl();
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

	/**
	 * local backup file path
	 * <p>
	 * path: ${user.dir}/lizard-data/conf/${mixedType}/${mixedName}.bak
	 *
	 * @param mixedName
	 * @param mixedType
	 * @return
	 */
	private File bakFile(String mixedName, MixedType mixedType) {
		String usrHome = SystemUtils.getUserHome().getAbsolutePath();
		String path    = usrHome + "/lizard-data/conf/" + mixedType + "/" + mixedName + ".bak";
		return new File(path);
	}

	/**
	 * save bakfile to local
	 *
	 * @param mixedName
	 * @param mixedType
	 * @param data
	 */
	private void saveBakFile(String mixedName, MixedType mixedType, String data) {
		try {
			FileUtils.write(bakFile(mixedName, mixedType), data, Charset.forName("UTF-8"), false);
		} catch (Exception e) {
			log.warn("Save mixed config bak file fail.", e);
		}
	}

	/**
	 * load bak file from local
	 *
	 * @param mixedName
	 * @param mixedType
	 * @return
	 */
	private String loadBakFile(String mixedName, MixedType mixedType) {
		try {
			return FileUtils.readFileToString(bakFile(mixedName, mixedType), Charset.forName("UTF-8"));
		} catch (Exception e) {
			log.warn("Load mixed config bak file from local fail.", e);
		}
		return null;
	}
}
