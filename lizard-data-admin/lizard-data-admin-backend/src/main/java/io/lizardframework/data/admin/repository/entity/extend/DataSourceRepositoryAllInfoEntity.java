package io.lizardframework.data.admin.repository.entity.extend;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-11-17
 */
@Data
public class DataSourceRepositoryAllInfoEntity {
	/**
	 * repository 名称
	 */
	private String repositoryName;
	/**
	 * repostiory 状态
	 */
	private String repositoryState;
	/**
	 * repository中atoms的负载均衡策略
	 */
	private String loadBalance;

	/**
	 * atom原子数据源名称
	 */
	private String atomName;
	/**
	 * 主从类型
	 */
	private String masterSlaveType;
	/**
	 * atom 状态
	 */
	private String atomState;
	/**
	 * 权重
	 */
	private int    weight;
	/**
	 * 数据源类型
	 */
	private String dataSourcePoolType;
	/**
	 * jdbc参数
	 */
	private String jdbcParams;
	/**
	 * 连接池配置
	 */
	private String poolConfig;

	/**
	 * 数据库host
	 */
	private String host;
	/**
	 * 数据库端口
	 */
	private int    port;
	/**
	 * 数据库用户名
	 */
	private String username;
	/**
	 * 数据库密码
	 */
	private String password;
	/**
	 * 数据库名称
	 */
	private String database;

}
