package io.lizardframework.data.orm.spring.register.meta;

import lombok.Data;

/**
 * @author xueqi
 * @date 2020-09-28
 */
@Data
public class MixedDataSourceRegisterMBean {

	private String mixedDataSourceName;
	private String mybatisSqlSessionFactory;

}
