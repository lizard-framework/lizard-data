package io.lizardframework.data.admin.model.resources;

import io.lizardframework.data.admin.repository.entity.ResourcesDatabaseEntity;
import lombok.Getter;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Getter
public class DatabaseInfoModel {

	private Long   id;
	private String dbType;
	private String dbName;
	private String dbHost;
	private int    dbPort;
	private String dbUsername;
	private String dbPassword;
	private String createTime;
	private String updateTime;


	/**
	 * 根据ResourcesDatabaseEntity构建对象
	 *
	 * @param entity
	 * @param isBasic
	 * @return
	 */
	public static DatabaseInfoModel build(ResourcesDatabaseEntity entity, boolean isBasic) {
		DatabaseInfoModel model = new DatabaseInfoModel();
		if (isBasic) {
			model.id = entity.getId();
			model.dbType = entity.getDbType();
			model.dbName = entity.getDbName();
			model.dbHost = entity.getDbHost();
			model.dbPort = entity.getDbPort();
			model.createTime = DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
			model.updateTime = DateFormatUtils.format(entity.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
		} else {
			model.dbUsername = entity.getDbUsername();
			model.dbPassword = entity.getDbPassword();
		}

		return model;
	}

	/**
	 * 设置解码后的敏感信息
	 *
	 * @param dbUsername
	 * @param dbPassword
	 */
	public void decryptAuthInfo(String dbUsername, String dbPassword) {
		this.dbUsername = dbUsername;
		this.dbPassword = dbPassword;
	}
}
