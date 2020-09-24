package io.lizardframework.data.admin.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xueqi
 * @date 2020-09-24
 */
@Configuration
public class RedissionConfiguration {

	@Value("${lizard.redis.server}")
	private String host;
	@Value("${lizard.redis.port}")
	private int    port;
	@Value("${lizard.redis.password}")
	private String password;

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		String url    = String.format("redis://%s:%s", host, port);
		config.useSingleServer()
				.setAddress(url)
				.setPassword(password)
				.setDatabase(0);
		return Redisson.create(config);
	}

}
