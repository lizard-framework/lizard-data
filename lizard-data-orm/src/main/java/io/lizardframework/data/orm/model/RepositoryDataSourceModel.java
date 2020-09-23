package io.lizardframework.data.orm.model;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.enums.State;
import lombok.Data;

import java.util.List;

/**
 * Group DataSource Model
 *
 * @author xueqi
 * @date 2020-09-07
 */
@Data
public class RepositoryDataSourceModel {

	/**
	 * 数据源组名称，将一个库的读写数据源抽象成一个分组；当使用分库功能时，需要根据策略找到group名称，之后根据读写策略找到具体的读写数据源
	 * <p>
	 * 一个混合数据源中必须唯一
	 */
	private String                    repositoryName;
	/**
	 * 数据源组状态，如果为offline则不初始化该数据源组
	 */
	private State                     state;
	/**
	 * 负载均衡类型，读写分离的多数据源的负载策略
	 */
	private LoadBalanceType           loadBalance;
	/**
	 * 原子数据源列表，一个库的数据源组包括多个原子数据源；当使用读写分离时，会根据负载策略使用相应的数据库连接
	 */
	private List<AtomDataSourceModel> atoms;

	/**
	 * check state is online
	 *
	 * @return
	 */
	public boolean isOnline() {
		return State.ONLINE.equals(this.state);
	}
}
