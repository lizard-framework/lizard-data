package io.lizardframework.data.orm.spring.register;

import io.lizardframework.data.enums.State;
import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.MasterSlaveDataSource;
import io.lizardframework.data.orm.datasource.meta.DataSourceMBean;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import io.lizardframework.data.orm.spring.register.pool.DataSourcePoolRegisterFactory;
import io.lizardframework.data.orm.spring.register.pool.IDataSourcePoolRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Mixed DataSource Spring Bean Register
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanRegister {

	/**
	 * do bean registry processor
	 *
	 * @param mixedDataSourceName
	 * @param beanDefinitionRegistry
	 */
	public void doRegistry(String mixedDataSourceName, BeanDefinitionRegistry beanDefinitionRegistry) {
		// 1. get mixed-data config
		MixedDataSourceModel mixedDataSourceModel = this.fetchAndConvertModel(mixedDataSourceName);

		// 2. registry mixed datasource bean
		this.registryMixDataSourceBean(mixedDataSourceModel, beanDefinitionRegistry);

		// 3. registry repository sharding and read write interceptor bean
		// 4. registry transaction manager bean
		// 5. registry mybatis bean and table sharding plugin bean
		// 6. register jdbcTemplate table sharding plugin bean
		// 7. report framework version and metric info
	}

	/**
	 * fetch mixed-data-source config and convert to MixedDataSourceModel model
	 *
	 * @param mixedDataSourceName
	 * @return
	 */
	private MixedDataSourceModel fetchAndConvertModel(String mixedDataSourceName) {
		// todo:
		MixedDataSourceModel mixedDataSourceModel = new MixedDataSourceModel();


		return mixedDataSourceModel;
	}

	/**
	 * registry datasource bean in spring context
	 *
	 * @param mixedDataSourceModel
	 * @param beanDefinitionRegistry
	 */
	private void registryMixDataSourceBean(MixedDataSourceModel mixedDataSourceModel, BeanDefinitionRegistry beanDefinitionRegistry) {
		String mixedDataSourceName = mixedDataSourceModel.getMixedName();

		Map<String, RuntimeBeanReference>  dataSources                  = new ManagedMap<>();
		Map<String, List<DataSourceMBean>> repositoryMasterAtomDsMapper = new HashMap<>();
		Map<String, List<DataSourceMBean>> repositorySlaveAtomDsMapper  = new HashMap<>();

		List<RepositoryDataSourceModel> repositories = mixedDataSourceModel.getRepositories();
		repositories.forEach(repository -> {
			String repositoryName = repository.getRepositoryName();

			// only process online statue
			if (State.ONLINE.equals(repository.getState())) {
				// init atoms datasource
				List<AtomDataSourceModel> atoms = repository.getAtoms();
				atoms.forEach(atom -> {
					String atomName = atom.getAtomName();

					// only process online state atom
					if (State.ONLINE.equals(atom.getState())) {
						IDataSourcePoolRegister dsRegistry          = DataSourcePoolRegisterFactory.getRegister(atom.getDataSourcePoolType());
						DataSourcePoolMBean     dataSourcePoolMBean = new DataSourcePoolMBean(mixedDataSourceModel, atom);
						String                  beanName            = mixedDataSourceName + "-" + repositoryName + "-" + atomName + "-" + atom.getMasterSlaveType();
						dsRegistry.doRegistry(beanName, dataSourcePoolMBean, beanDefinitionRegistry);
						dataSources.put(beanName, new RuntimeBeanReference(beanName));

						if (atom.isMaster()) {
							List<DataSourceMBean> masterAtomDsList = repositoryMasterAtomDsMapper.get(repositoryName);
							if (masterAtomDsList == null) {
								masterAtomDsList = new ArrayList<>();
							}
							masterAtomDsList.add(new DataSourceMBean(beanName, repositoryName, atom));
							repositoryMasterAtomDsMapper.put(repositoryName, masterAtomDsList);
						} else {
							List<DataSourceMBean> slaveAtomDsList = repositorySlaveAtomDsMapper.get(repositoryName);
							if (slaveAtomDsList == null) {
								slaveAtomDsList = new ArrayList<>();
							}
							slaveAtomDsList.add(new DataSourceMBean(beanName, repositoryName, atom));
							repositorySlaveAtomDsMapper.put(repositoryName, slaveAtomDsList);
						}
					} else {
						log.warn("Registry mixed-datasource:{}, repository:{}, atom:{} no processor, because state is:{}",
								mixedDataSourceName, repositoryName, atomName, atom.getState());
					}
				});
			} else {
				log.warn("Registry mixed-datasource:{}, repository:{} no prcessor, because state is:{}",
						mixedDataSourceName, repositoryName, repository.getState());
			}
		});

		// create DataSourceKey spring bean
		RootBeanDefinition dsKeyRootBeanDefinition = new RootBeanDefinition(DataSourceKey.class);
		dsKeyRootBeanDefinition.setScope(BeanDefinitionDsl.Scope.PROTOTYPE.toString().toLowerCase());
		dsKeyRootBeanDefinition.getPropertyValues().add("mixDataName", mixedDataSourceName);
		dsKeyRootBeanDefinition.getPropertyValues().add("repositoryMasterAtomDsMapper", repositoryMasterAtomDsMapper);
		dsKeyRootBeanDefinition.getPropertyValues().add("repositorySlaveAtomDsMapper", repositorySlaveAtomDsMapper);
		beanDefinitionRegistry.registerBeanDefinition(mixedDataSourceName + "-" + "DataSourceKey", dsKeyRootBeanDefinition);

		// create DataSource Spring Bean
		RootBeanDefinition datasourceRootBeanDefinition = new RootBeanDefinition(MasterSlaveDataSource.class);
		datasourceRootBeanDefinition.getPropertyValues().add("dataSourceKey", new RuntimeBeanReference(mixedDataSourceName + "-" + "DataSourceKey"));
		datasourceRootBeanDefinition.getPropertyValues().add("dataSources", dataSources);
		beanDefinitionRegistry.registerBeanDefinition(mixedDataSourceName, datasourceRootBeanDefinition);
	}
}
