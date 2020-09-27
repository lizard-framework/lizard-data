package io.lizardframework.data.admin.controller.model;

import lombok.Data;

/**
 * /api/orm/getMixedConfig request params
 *
 * @author xueqi
 * @date 2020-09-27
 */
@Data
public class ORMGetMixedConfigParams {

	private String application;
	private String hostname;
	private String mixedName;
	private String sdkVersion;

}
