package io.lizardframework.data.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-25
 */
public class JSONUtils {
	private static final Gson DEFAULT_GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, type, jsonSerializationContext) -> {
				if (src == src.longValue())
					return new JsonPrimitive(src.longValue());
				return new JsonPrimitive(src);
			})
			.create();

	public static Map<String, Object> json2Map(String json) {
		Map<String, Object> mapper = null;

		if (StringUtils.isNotEmpty(json)) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<String, Object>>() {
			}.getType();
			mapper = DEFAULT_GSON.fromJson(json, type);
		}

		return mapper;
	}

	public static String toJSONString(Object object) {
		return DEFAULT_GSON.toJson(object);
	}

	public static Gson getDefaultGson() {
		return DEFAULT_GSON;
	}
}
