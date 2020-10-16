package io.lizardframework.data.admin.model;

import io.lizardframework.data.admin.dao.entity.DbInfoEntity;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Data
public class DataBaseInfoModel {

	private Long   id;
	private String dbType;
	private String dbName;
	private String dbHost;
	private int    dbPort;
	private String dbUsername;
	private String dbPassword;
	private String createTime;
	private String updateTime;


	public DataBaseInfoModel(DbInfoEntity entity) {
		this.id = entity.getId();
		this.dbType = entity.getDbType();
		this.dbName = entity.getDbName();
		this.dbHost = entity.getDbHost();
		this.dbPort = entity.getDbPort();
		this.dbUsername = entity.getDbUsername();
		this.dbPassword = entity.getDbPassword();
		this.createTime = DateFormatUtils.format(entity.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
		this.updateTime = DateFormatUtils.format(entity.getUpdateTime(), "yyyy-MM-dd HH:mm:ss");
	}

}
