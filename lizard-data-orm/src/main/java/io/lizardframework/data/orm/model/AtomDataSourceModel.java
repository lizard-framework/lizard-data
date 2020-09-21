package io.lizardframework.data.orm.model;

import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import lombok.Data;

import java.util.Map;

/**
 * Atom DataSource Model
 *
 * @author xueqi
 * @date 2020-09-07
 */
@Data
public class AtomDataSourceModel {

	/**
	 * 原子数据源名称，一个group中必须唯一
	 */
	private String              atomName;
	/**
	 * 数据库连接host地址
	 */
	private String              host;
	/**
	 * 数据库连接端口信息
	 */
	private int                 port;
	/**
	 * 数据库连接用户名
	 */
	private String              username;
	/**
	 * 数据库连接密码
	 */
	private String              password;
	/**
	 * 数据库名称
	 */
	private String              database;
	/**
	 * 数据库连接参数
	 */
	private String              params;
	/**
	 * 是否为主库
	 */
	private boolean             isMaster;
	/**
	 * 原子数据源状态，如果为offline则不初始化
	 */
	private State               state;
	/**
	 * 权重比例
	 */
	private int                 weight;
	/**
	 * 数据库连接池类型
	 */
	private DataSourcePoolType  dataSourcePoolType;
	/**
	 * DataSource Pool Config Mapper
	 */
	private Map<String, Object> poolConfigMapper;
}
