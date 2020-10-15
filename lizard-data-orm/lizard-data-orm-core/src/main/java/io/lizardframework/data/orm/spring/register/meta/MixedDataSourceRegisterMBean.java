package io.lizardframework.data.orm.spring.register.meta;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-09-28
 */
@Data
public class MixedDataSourceRegisterMBean {
	/**
	 * mixed datasource name
	 */
	private String mixedDataSourceName;
	/**
	 * mybatis sqlsessionfactorybean name
	 */
	private String mybatisSqlSessionFactory;
}
