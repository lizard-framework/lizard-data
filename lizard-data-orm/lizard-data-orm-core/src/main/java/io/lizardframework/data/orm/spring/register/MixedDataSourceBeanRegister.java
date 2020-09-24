package io.lizardframework.data.orm.spring.register;

import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.MasterSlaveDataSource;
import io.lizardframework.data.orm.datasource.RepositoryShardingDataSource;
import io.lizardframework.data.orm.datasource.meta.DataSourceMBean;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import io.lizardframework.data.orm.spring.register.pool.DataSourcePoolRegisterFactory;
import io.lizardframework.data.orm.spring.register.pool.IDataSourcePoolRegister;
import io.lizardframework.data.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.context.support.BeanDefinitionDsl;

import java.util.*;

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

		// registry atom datasource pool in spring context
		this.registryAtomDataSource(mixedDataSourceModel, beanDefinitionRegistry, dataSources, repositoryMasterAtomDsMapper, repositorySlaveAtomDsMapper);


		// create DataSourceKey spring bean - PROTOTYPE, each MixedDataSource have one DataSourceKey, no share
		String dataSourceKeyBeanName = BeanUtils.genBeanName(mixedDataSourceName, "DataSourceKey");
		BeanUtils.registryBean(dataSourceKeyBeanName, beanDefinitionRegistry, DataSourceKey.class, BeanDefinitionDsl.Scope.PROTOTYPE, Arrays.asList(
				new PropertyValue("mixDataName", mixedDataSourceName),
				new PropertyValue("repositoryMasterAtomDsMapper", repositoryMasterAtomDsMapper),
				new PropertyValue("repositorySlaveAtomDsMapper", repositorySlaveAtomDsMapper)
		));

		// create DataSource Spring Bean, if only one repository, create MasterSlaveDataSource
		Class datasourceClazz = mixedDataSourceModel.getRepositories().size() == 1 ? MasterSlaveDataSource.class : RepositoryShardingDataSource.class;
		BeanUtils.registryBean(mixedDataSourceName, beanDefinitionRegistry, datasourceClazz, Arrays.asList(
				new PropertyValue("dataSourceKey", new RuntimeBeanReference(dataSourceKeyBeanName)),
				new PropertyValue("dataSources", dataSources)
		));
	}

	/**
	 * registry atom datasource
	 *
	 * @param mixedDataSourceModel
	 * @param beanDefinitionRegistry
	 * @param dataSources
	 * @param repositoryMasterAtomDsMapper
	 * @param repositorySlaveAtomDsMapper
	 */
	private void registryAtomDataSource(MixedDataSourceModel mixedDataSourceModel,
	                                    BeanDefinitionRegistry beanDefinitionRegistry,
	                                    Map<String, RuntimeBeanReference> dataSources,
	                                    Map<String, List<DataSourceMBean>> repositoryMasterAtomDsMapper,
	                                    Map<String, List<DataSourceMBean>> repositorySlaveAtomDsMapper) {
		String mixedDataSourceName = mixedDataSourceModel.getMixedName();

		// each repository create atom datasource and registry in spring context
		List<RepositoryDataSourceModel> repositories = mixedDataSourceModel.getRepositories();
		for (RepositoryDataSourceModel repository : repositories) {
			String repositoryName = repository.getRepositoryName();

			// only process online statue
			if (!repository.isOnline()) {
				log.warn("Registry mixed-datasource:{}, repository:{} no prcessor, because state is:{}", mixedDataSourceName, repositoryName, repository.getState());
				continue;
			}

			// init atoms datasource
			List<AtomDataSourceModel> atoms = repository.getAtoms();
			for (AtomDataSourceModel atom : atoms) {
				String atomName = atom.getAtomName();

				// only process online statue
				if (!atom.isOnline()) {
					log.warn("Registry mixed-datasource:{}, repository:{}, atom:{} no processor, because state is:{}", mixedDataSourceName, repositoryName, atomName, atom.getState());
					continue;
				}

				// get datasource pool register
				IDataSourcePoolRegister dsRegistry          = DataSourcePoolRegisterFactory.getRegister(atom.getDataSourcePoolType());
				DataSourcePoolMBean     dataSourcePoolMBean = new DataSourcePoolMBean(mixedDataSourceModel, atom);
				String                  atomDsBeanName      = BeanUtils.genBeanName(mixedDataSourceName, repositoryName, atomName, atom.getMasterSlaveType());
				dsRegistry.doRegistry(atomDsBeanName, dataSourcePoolMBean, beanDefinitionRegistry);
				dataSources.put(atomDsBeanName, new RuntimeBeanReference(atomDsBeanName));

				// add DataSourceMBean to DataSourceKey atom datasource mapper
				if (atom.isMaster()) {
					List<DataSourceMBean> masterAtomDsList = repositoryMasterAtomDsMapper.get(repositoryName);
					if (masterAtomDsList == null) {
						masterAtomDsList = new ArrayList<>();
					}
					masterAtomDsList.add(new DataSourceMBean(atomDsBeanName, repositoryName, atom));
					repositoryMasterAtomDsMapper.put(repositoryName, masterAtomDsList);
				} else {
					List<DataSourceMBean> slaveAtomDsList = repositorySlaveAtomDsMapper.get(repositoryName);
					if (slaveAtomDsList == null) {
						slaveAtomDsList = new ArrayList<>();
					}
					slaveAtomDsList.add(new DataSourceMBean(atomDsBeanName, repositoryName, atom));
					repositorySlaveAtomDsMapper.put(repositoryName, slaveAtomDsList);
				}
			}
		}
	}
}
