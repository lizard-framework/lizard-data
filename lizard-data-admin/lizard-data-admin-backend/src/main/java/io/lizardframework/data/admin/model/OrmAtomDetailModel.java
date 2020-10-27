package io.lizardframework.data.admin.model;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.utils.JSONUtils;
import lombok.Data;

/**
 * @author xueqi
 * @date 2020-10-27
 */
@Data
public class OrmAtomDetailModel {
	/**
	 * atom datasource name，must be uninq in repository
	 */
	private String             atomName;
	/**
	 * database connection host address
	 */
	private String             host;
	/**
	 * database connection port
	 */
	private int                port;
	/**
	 * database connection username
	 */
	private String             username;
	/**
	 * database connection password
	 */
	private String             password;
	/**
	 * database connection schema name
	 */
	private String             database;
	/**
	 * database connection params
	 */
	private String             params;
	/**
	 * datasource is master or slave flag
	 */
	private MasterSlaveType    masterSlaveType;
	/**
	 * atom status，if 'offline' not init
	 */
	private State              state;
	/**
	 * atom loadbalance weight
	 */
	private int                weight;
	/**
	 * datasource connection pool type
	 */
	private DataSourcePoolType dataSourcePoolType;
	/**
	 * DataSource Pool Config Mapper
	 */
	private String             poolConfig;

	public OrmAtomDetailModel(AtomDataSourceModel model) {
		this.atomName = model.getAtomName();
		this.host = model.getHost();
		this.port = model.getPort();
		this.username = model.getUsername();
		this.password = model.getPassword();
		this.database = model.getDatabase();
		this.params = model.getParams();
		this.masterSlaveType = model.getMasterSlaveType();
		this.state = model.getState();
		this.weight = model.getWeight();
		this.dataSourcePoolType = model.getDataSourcePoolType();
		this.poolConfig = JSONUtils.toPrettyJSONString(model.getPoolConfigMapper());
	}
}
