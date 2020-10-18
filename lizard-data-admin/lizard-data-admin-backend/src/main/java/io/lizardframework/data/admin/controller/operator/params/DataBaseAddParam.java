package io.lizardframework.data.admin.controller.operator.params;

import io.lizardframework.data.admin.dao.entity.DbInfoEntity;
import lombok.Data;

import java.util.Date;

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

	public DbInfoEntity toEntity() {
		Date date = new Date();

		DbInfoEntity entity = new DbInfoEntity();
		entity.setDbType(this.dbType);
		entity.setDbName(this.dbName);
		entity.setDbHost(this.dbHost);
		entity.setDbPort(this.dbPort);
		entity.setDbUsername(this.dbUsername);
		entity.setDbPassword(this.dbPassword);
		entity.setCreateTime(date);
		entity.setUpdateTime(date);

		return entity;
	}
}
