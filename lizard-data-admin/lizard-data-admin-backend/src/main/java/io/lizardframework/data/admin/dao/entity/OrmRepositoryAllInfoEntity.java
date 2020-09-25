package io.lizardframework.data.admin.dao.entity;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-09-25
 */
@Data
public class OrmRepositoryAllInfoEntity {
	private String repositoryName;
	private String repositoryState;
	private String loadBalance;

	private String atomName;
	private String masterSlaveType;
	private String atomState;
	private int    weight;
	private String dataSourcePoolType;
	private String jdbcParams;
	private String poolConfig;

	private String host;
	private int    port;
	private String username;
	private String password;
	private String database;
}
