package io.lizardframework.data.admin.controller.resourcesManager.application.params;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-11-01
 */
@Data
public class ApplicationAddParam {

	private String applicationName;
	private String applicationDesc;
	private String owner;
}
