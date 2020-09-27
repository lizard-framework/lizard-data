package io.lizardframework.data.orm.model;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.utils.JSONUtils;
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

	/**
	 * check online state
	 *
	 * @return
	 */
	public boolean isOnline() {
		return State.ONLINE.equals(this.state);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(super.toString());
		builder.append("[");
		builder.append("atomName=").append(atomName).append(",");
		builder.append("host=").append(host).append(",");
		builder.append("port=").append(port).append(",");
		builder.append("username=").append(username).append(",");
		builder.append("password=").append("******").append(",");
		builder.append("database=").append(database).append(",");
		builder.append("params=").append(params).append(",");
		builder.append("masterSlaveType=").append(masterSlaveType).append(",");
		builder.append("state=").append(state).append(",");
		builder.append("weight=").append(weight).append(",");
		builder.append("dataSourcePoolType=").append(dataSourcePoolType).append(",");
		builder.append("poolConfigMapper=").append(JSONUtils.toJSONString(poolConfigMapper));
		builder.append("]");

		return builder.toString();
	}
}
