package io.lizardframework.data.orm.spring.register.pool;

import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.orm.spring.register.pool.impl.DruidDataSourcePoolRegister;
import io.lizardframework.data.orm.spring.register.pool.impl.HikariCPDataSourcePoolRegister;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class DataSourcePoolRegisterFactory {
	private static final Map<DataSourcePoolType, IDataSourcePoolRegister> REGISTER_MAP = new HashMap<>();

	static {
		REGISTER_MAP.put(DataSourcePoolType.DRUID, new DruidDataSourcePoolRegister());
		REGISTER_MAP.put(DataSourcePoolType.HikariCP, new HikariCPDataSourcePoolRegister());
	}

	private DataSourcePoolRegisterFactory() {
	}

	/**
	 * get DataSourcePoolRegister
	 *
	 * @param type
	 * @return
	 */
	public static IDataSourcePoolRegister getRegister(DataSourcePoolType type) {
		IDataSourcePoolRegister registry = REGISTER_MAP.get(type);
		log.info("Selector datasource pool register, type:{}, register:{}", type, registry);

		if (registry == null) {
			throw new IllegalArgumentException("not support datasource pool type: " + type);
		}

		return registry;
	}

}
