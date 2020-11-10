package io.lizardframework.data.admin.controller.applicationConfig.datasource.params;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-11-04
 */
public class OrmMixedAddStep1Param {

	/**
	 * 数据源名称
	 */
	private String       mixedDataSourceName;
	/**
	 * 状态
	 */
	private String       state;
	/**
	 * 数据源所属数据库类型
	 */
	private String       dbType;
	/**
	 * 数据源描述
	 */
	private String       mixedDataSourceDesc;
	/**
	 * 数据源所属应用列表
	 */
	private List<String> applicationList;
}
