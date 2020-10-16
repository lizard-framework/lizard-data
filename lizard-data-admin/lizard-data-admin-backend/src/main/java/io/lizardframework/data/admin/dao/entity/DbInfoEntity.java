package io.lizardframework.data.admin.dao.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author xueqi
 * @date 2020-10-16
 */
@Data
public class DbInfoEntity {

	private Long   id;
	private String dbType;
	private String dbName;
	private String dbHost;
	private int    dbPort;
	private String dbUsername;
	private String dbPassword;
	private Date   createTime;
	private Date   updateTime;
}
