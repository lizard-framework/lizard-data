package io.lizardframework.data.orm.datasource.strategy;

import io.lizardframework.data.orm.enums.ReadWriteType;
import lombok.Data;

/**
 * 数据源选择策略
 *
 * @author xueqi
 * @date 2020-09-13
 */
@Data
public class DataSourceStrategy {
	/**
	 * 读写类型: 默认为写库
	 */
	private ReadWriteType readWriteType = ReadWriteType.WRITE;
	/**
	 * 分库sharding key
	 */
	private String        repositoryShardingKey;
	/**
	 * 数据源key - 实际的数据源bean name
	 */
	private String        dataSourceKey;
	/**
	 * 是否在事务中
	 */
	private boolean       transaction   = false;

	public DataSourceStrategy(String dataSourceKey) {
		this.dataSourceKey = dataSourceKey;
	}
}
