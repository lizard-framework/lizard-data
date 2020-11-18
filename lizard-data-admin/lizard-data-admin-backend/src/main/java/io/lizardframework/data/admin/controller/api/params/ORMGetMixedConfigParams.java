package io.lizardframework.data.admin.controller.api.params;

import lombok.Getter;
import lombok.Setter;

/**
 * /api/orm/getMixedConfig request params
 *
 * @author xueqi
 * @date 2020-09-27
 */
@Getter
@Setter
public class ORMGetMixedConfigParams {

	private String application;
	private String hostname;
	private String mixedName;
	private String sdkVersion;

	@Override
	public String toString() {
		return "ORMGetMixedConfigParams{" +
				"application='" + application + '\'' +
				", hostname='" + hostname + '\'' +
				", mixedName='" + mixedName + '\'' +
				", sdkVersion='" + sdkVersion + '\'' +
				'}';
	}
}
