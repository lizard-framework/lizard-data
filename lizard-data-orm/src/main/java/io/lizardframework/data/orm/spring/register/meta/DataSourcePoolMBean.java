package io.lizardframework.data.orm.spring.register.meta;

import io.lizardframework.data.orm.enums.DBType;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import lombok.Getter;

import java.util.Map;

/**
 * datasource pool info meta bean
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Getter
public class DataSourcePoolMBean {
	/**
	 * database type
	 */
	private DBType              dbType;
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
	 * DataSource Pool Config Mapper
	 */
	private Map<String, Object> poolConfigMapper;

	public DataSourcePoolMBean(MixedDataSourceModel mixedDataSourceModel, AtomDataSourceModel atomDataSourceModel) {
		this.dbType = mixedDataSourceModel.getType();
		this.host = atomDataSourceModel.getHost();
		this.port = atomDataSourceModel.getPort();
		this.username = atomDataSourceModel.getUsername();
		this.password = atomDataSourceModel.getPassword();
		this.database = atomDataSourceModel.getDatabase();
		this.params = atomDataSourceModel.getParams();
		this.poolConfigMapper = atomDataSourceModel.getPoolConfigMapper();
	}
}
