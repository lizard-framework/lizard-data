package io.lizardframework.data.orm.model;

import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DBType;
import lombok.Data;

import java.util.List;

/**
 * Mixed DataSource Config Model
 *
 * @author xueqi
 * @date 2020-09-07
 */
@Data
public class MixedDataSourceModel {
	/**
	 * 混合数据源DataSource在Spring容器中的Bean id，事务管理器、MyBatis会话工厂、JdbcTemplete等通过该id引用DataSource
	 */
	private String                          mixedName;
	/**
	 * 配置状态，如果为OFFLINE，此配置不会初始化
	 */
	private State                           state;
	/**
	 * 数据源连接的数据库类型
	 */
	private DBType                          type;
	/**
	 * 数据源组列表，一个混合数据源中有多个数据库数据源的抽象
	 */
	private List<RepositoryDataSourceModel> repositories;
}
