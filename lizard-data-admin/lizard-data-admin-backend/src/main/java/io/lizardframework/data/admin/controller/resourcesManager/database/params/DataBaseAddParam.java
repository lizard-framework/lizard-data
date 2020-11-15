package io.lizardframework.data.admin.controller.resourcesManager.database.params;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-10-18
 */
@Data
public class DataBaseAddParam {

	private String dbType;
	private String dbName;
	private String dbHost;
	private int    dbPort;
	private String dbUsername;
	private String dbPassword;
}
