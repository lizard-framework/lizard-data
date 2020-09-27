package io.lizardframework.data.orm.spring.register.pool;

import io.lizardframework.data.orm.enums.DBType;
import io.lizardframework.data.orm.enums.DataSourcePoolType;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Properties;

/**
 * DataSource Pool Register
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public abstract class IDataSourcePoolRegister {

	/**
	 * registry datasource pool bean in spring context
	 *
	 * @param beanName
	 * @param dataSourcePoolMBean
	 * @param beanDefinitionRegistry
	 */
	public abstract void doRegistry(String beanName, DataSourcePoolMBean dataSourcePoolMBean, BeanDefinitionRegistry beanDefinitionRegistry);

	/**
	 * return support DataSourcePoolType
	 *
	 * @return
	 */
	public abstract DataSourcePoolType support();

	// ----------- method ----------- //

	/**
	 * build jdbc connection url
	 *
	 * @param dataSourcePoolMBean
	 * @return
	 */
	protected String buildJdbcUrl(DataSourcePoolMBean dataSourcePoolMBean) {
		DBType dbType = dataSourcePoolMBean.getDbType();

		StringBuilder urlBuilder = new StringBuilder(dbType.getJdbcPrefix());
		urlBuilder.append(dataSourcePoolMBean.getHost())
				.append(":")
				.append(dataSourcePoolMBean.getPort())
				.append("/")
				.append(dataSourcePoolMBean.getDatabase());
		if (StringUtils.isNotEmpty(dataSourcePoolMBean.getParams())) {
			urlBuilder.append("?").append(dataSourcePoolMBean.getParams());
		}

		log.debug("Registry jdbc url:{}", urlBuilder.toString());
		return urlBuilder.toString();
	}

	/**
	 * convert pool config
	 *
	 * @param poolConfigMapper
	 * @return
	 */
	protected Properties pollConfigProperties(Map<String, Object> poolConfigMapper) {
		Properties properties = new Properties();
		if (!CollectionUtils.isEmpty(poolConfigMapper)) {
			for (Map.Entry<String, Object> entry : poolConfigMapper.entrySet()) {
				if (entry.getValue() == null) {
					properties.setProperty(entry.getKey(), null);
				} else {
					// checker double value to long
					if (entry.getValue() instanceof Double) {
						Double src = (Double) entry.getValue();
						if (src == src.longValue()) {
							properties.setProperty(entry.getKey(), src.longValue() + "");
							continue;
						}
					}

					properties.setProperty(entry.getKey(), entry.getValue().toString());
				}
			}
		}

		return properties;
	}
}
