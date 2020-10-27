package io.lizardframework.data.utils;

import com.google.gson.*;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

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

	private static final Gson PRETTY_PRINT_GSON = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(Double.class, (JsonSerializer<Double>) (src, type, jsonSerializationContext) -> {
				if (src == src.longValue())
					return new JsonPrimitive(src.longValue());
				return new JsonPrimitive(src);
			}).create();

	public static Map<String, Object> json2Map(String json) {
		Map<String, Object> mapper = null;

		if (StringUtils.isNotEmpty(json)) {
			Type type = new TypeToken<Map<String, Object>>() {
			}.getType();
			mapper = DEFAULT_GSON.fromJson(json, type);
		}

		return mapper;
	}

	public static String toJSONString(Object object) {
		return DEFAULT_GSON.toJson(object);
	}

	public static String toPrettyJSONString(Object object) {
		return PRETTY_PRINT_GSON.toJson(object);
	}

	public static Gson getDefaultGson() {
		return DEFAULT_GSON;
	}
}
