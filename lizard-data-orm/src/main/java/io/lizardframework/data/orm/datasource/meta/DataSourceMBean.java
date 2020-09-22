package io.lizardframework.data.orm.datasource.meta;

import lombok.Getter;

/**
 * Save Selector DataSource Condition Bean
 *
 * @author xueqi
 * @date 2020-09-21
 */
@Getter
public class DataSourceMBean {

	/**
	 * 数据源Bean Name
	 */
	private String  beanName;
	/**
	 * 原子数据源名称，一个group中必须唯一
	 */
	private String  atomName;
	/**
	 * 是否为主库
	 */
	private boolean isMaster;
	/**
	 * 权重比例
	 */
	private int     weight;
}
