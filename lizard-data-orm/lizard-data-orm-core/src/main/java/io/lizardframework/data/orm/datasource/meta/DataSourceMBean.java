package io.lizardframework.data.orm.datasource.meta;

import io.lizardframework.data.enums.MasterSlaveType;
import io.lizardframework.data.loadbalance.LbNode;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import lombok.Getter;

/**
 * Save Selector DataSource Condition Bean
 *
 * @author xueqi
 * @date 2020-09-21
 */
@Getter
public class DataSourceMBean implements LbNode {

	/**
	 * datasource bean name
	 */
	private String          beanName;
	/**
	 * repository name
	 */
	private String          repositoryName;
	/**
	 * atom name
	 */
	private String          atomName;
	/**
	 * master flag
	 */
	private MasterSlaveType masterSlaveType;
	/**
	 * loadbalance weight
	 */
	private int             weight;

	public DataSourceMBean(String beanName, String repositoryName, AtomDataSourceModel atomDataSourceModel) {
		this.beanName = beanName;
		this.repositoryName = repositoryName;
		this.atomName = atomDataSourceModel.getAtomName();
		this.masterSlaveType = atomDataSourceModel.getMasterSlaveType();
		this.weight = atomDataSourceModel.getWeight();
	}

	@Override
	public int weight() {
		return this.weight;
	}

	@Override
	public String toString() {
		return "DataSourceMBean{" +
				"beanName='" + beanName + '\'' +
				", repositoryName='" + repositoryName + '\'' +
				", atomName='" + atomName + '\'' +
				", masterSlaveType=" + masterSlaveType +
				", weight=" + weight +
				'}';
	}
}
