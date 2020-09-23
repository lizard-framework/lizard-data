package io.lizardframework.data.orm.model;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import lombok.Data;

import java.util.HashMap;
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
	 * atom datasource name，must be uninq in repository
	 */
	private String              atomName;
	/**
	 * database connection host address
	 */
	private String              host;
	/**
	 * database connection port
	 */
	private int                 port;
	/**
	 * database connection username
	 */
	private String              username;
	/**
	 * database connection password
	 */
	private String              password;
	/**
	 * database connection schema name
	 */
	private String              database;
	/**
	 * database connection params
	 */
	private String              params;
	/**
	 * datasource is master or slave flag
	 */
	private MasterSlaveType     masterSlaveType;
	/**
	 * atom status，if 'offline' not init
	 */
	private State               state;
	/**
	 * atom loadbalance weight
	 */
	private int                 weight;
	/**
	 * datasource connection pool type
	 */
	private DataSourcePoolType  dataSourcePoolType;
	/**
	 * DataSource Pool Config Mapper
	 */
	private Map<String, Object> poolConfigMapper = new HashMap<>();

	/**
	 * return master type
	 *
	 * @return
	 */
	public boolean isMaster() {
		return MasterSlaveType.MASTER.equals(this.masterSlaveType);
	}
}
