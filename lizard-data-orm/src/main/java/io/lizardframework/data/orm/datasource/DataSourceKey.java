package io.lizardframework.data.orm.datasource;

import lombok.Setter;

/**
 * DataSource Key : 获取数据源Key工具类、LB
 *
 * @author xueqi
 * @date 2020-09-10
 */
public class DataSourceKey {

	/**
	 * 读写数据源所属的mix-data名称
	 */
	@Setter
	private String mixDataName;
	/**
	 * 读写数据源所属的group名称
	 */
	@Setter
	private String groupName;

	/**
	 * 获取数据源key值
	 *
	 * @return
	 */
	public String getDataSourceKey() {
		// todo:
		return "";
	}

	/**
	 * 获取分库数据源key值
	 *
	 * @return
	 */
	public String getRepositoryShardingKey() {
		// todo:
		return "";
	}
}
