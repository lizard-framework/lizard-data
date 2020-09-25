package io.lizardframework.data.admin.dao.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Data
public class OrmMixedEntity {

	private Long      id;
	private String    mixedName;
	private String    mixedDesc;
	private String    state;
	private String    dbType;
	private String    createUser;
	private Timestamp createTime;
	private Timestamp updateTime;
}
