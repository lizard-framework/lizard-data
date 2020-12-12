package io.lizardframework.data.admin.support.configuration;

import io.lizardframework.data.utils.JSONUtils;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;

/**
 * @author xueqi
 * @date 2020-09-27
 */
@Configuration
public class MvcConfiguration {

	@Bean
	public HttpMessageConverters gsonHttpMessageConverters() {
		ArrayList<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

		GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter(JSONUtils.getDefaultGson());
		messageConverters.add(gsonHttpMessageConverter);

		return new HttpMessageConverters(true, messageConverters);
	}

}
