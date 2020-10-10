package io.lizardframework.data.orm.spring.register.beans;

import io.lizardframework.data.orm.datasource.BaseRoutingDataSource;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import io.lizardframework.data.orm.support.utils.DataSourceUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * when ContextRefreshedEvent(All Bean has been instantiated), Warm up DataSource
 *
 * @author xueqi
 * @date 2020-10-10
 */
@Slf4j
public class MixedDataSourceWarmupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Setter
	private List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!CollectionUtils.isEmpty(mixedDataSourceRegisterMBeanList)) {
			ApplicationContext applicationContext = event.getApplicationContext();

			for (MixedDataSourceRegisterMBean registerMBean : mixedDataSourceRegisterMBeanList) {
				String mixedDataSourceName = registerMBean.getMixedDataSourceName();

				BaseRoutingDataSource baseRoutingDataSource = (BaseRoutingDataSource) applicationContext.getBean(mixedDataSourceName);
				Map<Object, Object>   datasources           = baseRoutingDataSource.getDataSources();
				if (!CollectionUtils.isEmpty(datasources)) {
					for (Object dsName : datasources.keySet()) {
						log.debug("Warming up mixed datasource:{}, atom datasource:{}", mixedDataSourceName, dsName);

						DataSource dataSource = (DataSource) applicationContext.getBean(dsName.toString());
						validateDataSource(dataSource);
					}
				}
			}
		}
	}

	private void validateDataSource(DataSource dataSource) {
		try {
			DataSourceUtil.validateConnection(dataSource);
		} catch (Exception e) {
			throw new ContextedRuntimeException(e);
		}
	}
}
