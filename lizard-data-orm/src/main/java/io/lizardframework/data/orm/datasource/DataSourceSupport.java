package io.lizardframework.data.orm.datasource;

import io.lizardframework.data.orm.enums.ReadWriteType;

/**
 * DataSourceSupport
 *
 * @author xueqi
 * @date 2020-09-10
 */
public class DataSourceSupport {

	/**
	 * 构建数据源名称
	 *
	 * @param mixDataName
	 * @param groupName
	 * @param atomName
	 * @param type
	 * @return
	 */
	public static String dataSourceName(String mixDataName, String groupName, String atomName, ReadWriteType type) {
		String dsName = mixDataName + "_" + groupName + "_" + atomName + "_" + type.getValue();
		return dsName;
	}

}
