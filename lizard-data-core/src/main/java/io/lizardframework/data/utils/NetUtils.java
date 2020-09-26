package io.lizardframework.data.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.InetAddress;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-26
 */
@Slf4j
public class NetUtils {

	public static String post(Map<String, String> params, String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();

		HttpPost post = new HttpPost(url);

		String       requestJson  = JSONUtils.toJSONString(params);
		StringEntity stringEntity = new StringEntity(requestJson, "UTF-8");
		stringEntity.setContentType("application/json");

		CloseableHttpResponse response = null;
		try {
			post.setEntity(stringEntity);
			post.setConfig(RequestConfig.custom()
					.setConnectTimeout(5000)
					.setSocketTimeout(5000)
					.setConnectionRequestTimeout(5000)
					.build());
			response = httpclient.execute(post);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				return EntityUtils.toString(entity, "UTF-8");
			}
		} catch (Exception e) {
			log.warn("Http post url:{} fail. params:{}", url, requestJson, e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (Exception e) {
					// do nothing
				}
			}
		}

		return null;
	}

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			log.warn("Get localhost name error.", e);
		}

		return "unknown";
	}
}
