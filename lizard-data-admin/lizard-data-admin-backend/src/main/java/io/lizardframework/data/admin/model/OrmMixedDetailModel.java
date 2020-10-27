package io.lizardframework.data.admin.model;

import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.enums.DBType;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xueqi
 * @date 2020-10-27
 */
@Data
public class OrmMixedDetailModel {
	/**
	 * 混合数据源DataSource在Spring容器中的Bean id，事务管理器、MyBatis会话工厂、JdbcTemplete等通过该id引用DataSource
	 */
	private String                         mixedName;
	/**
	 * 配置状态，如果为OFFLINE，此配置不会初始化
	 */
	private State                          state;
	/**
	 * 数据源连接的数据库类型
	 */
	private DBType                         type;
	/**
	 * 数据源组列表，一个混合数据源中有多个数据库数据源的抽象
	 */
	private List<OrmRepositoryDetailModel> repositories;
	/**
	 * 描述
	 */
	private String                         mixedDesc;
	/**
	 * 所属应用配置
	 */
	private List<String>                   applicationList;
	/**
	 * 创建人
	 */
	private String                         createUser;
	/**
	 * 创建时间
	 */
	private String                         createTime;

	public OrmMixedDetailModel(MixedDataSourceModel model) {
		this.setMixedName(model.getMixedName());
		this.setState(model.getState());
		this.setType(model.getType());
		this.setRepositories(
				model.getRepositories().stream().map(repository -> new OrmRepositoryDetailModel(repository)).collect(Collectors.toList())
		);
	}
}
