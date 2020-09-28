package io.lizardframework.data.orm.spring.register;

import io.lizardframework.data.enums.MixedType;
import io.lizardframework.data.orm.Constants;
import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.MasterSlaveDataSource;
import io.lizardframework.data.orm.datasource.RepositoryShardingDataSource;
import io.lizardframework.data.orm.datasource.meta.DataSourceMBean;
import io.lizardframework.data.orm.interceptor.MasterSlaveAnnotationInterceptor;
import io.lizardframework.data.orm.interceptor.RepositoryShardingAnnotationInterceptor;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.orm.spring.register.beans.MixedDataBeanFactoryPostProcessor;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import io.lizardframework.data.orm.spring.register.pool.DataSourcePoolRegisterFactory;
import io.lizardframework.data.orm.spring.register.pool.IDataSourcePoolRegister;
import io.lizardframework.data.remoting.MixedConfigFetcher;
import io.lizardframework.data.remoting.SecurityFetcher;
import io.lizardframework.data.utils.BeanUtils;
import io.lizardframework.data.utils.JSONUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Mixed DataSource Spring Bean Register
 *
 * @author xueqi
 * @date 2020-09-22
 */
@Slf4j
public class MixedDataSourceBeanRegister implements Constants {

	/**
	 * do bean registry processor
	 *
	 * @param mixedDataSourceName
	 * @param beanDefinitionRegistry
	 */
	public void doRegistry(String mixedDataSourceName, BeanDefinitionRegistry beanDefinitionRegistry) throws Exception {
		// get mixed-data config
		MixedDataSourceModel mixedDataSourceModel = this.fetchAndConvertModel(mixedDataSourceName);

		// registry BeanFactoryPostPorcessor for modify bean definition
		this.registryBeanFactoryPostProcessor(beanDefinitionRegistry);

		// registry mixed datasource bean
		this.registryMixDataSourceBean(mixedDataSourceModel, beanDefinitionRegistry);

		// registry repository sharding and master slave interceptor bean
		this.registryRepositoryShardingInterceptor(beanDefinitionRegistry);
		this.registryMasterSlaveAnnotationInterceptor(beanDefinitionRegistry);

		// registry mybatis bean and table sharding plugin bean

		// register jdbcTemplate table sharding plugin bean
		// report framework version and metric info
	}

	/**
	 * fetch mixed-data-source config and convert to MixedDataSourceModel model
	 *
	 * @param mixedDataSourceName
	 * @return
	 */
	private MixedDataSourceModel fetchAndConvertModel(String mixedDataSourceName) throws Exception {
		String               modelJson            = MixedConfigFetcher.getInstance().getMixedConfig(mixedDataSourceName, MixedType.ORM);
		MixedDataSourceModel mixedDataSourceModel = JSONUtils.getDefaultGson().fromJson(modelJson, MixedDataSourceModel.class);

		// encode database password and username
		if (!CollectionUtils.isEmpty(mixedDataSourceModel.getRepositories())) {
			mixedDataSourceModel.getRepositories().forEach(repository -> {
				List<AtomDataSourceModel> atoms = repository.getAtoms();
				if (!CollectionUtils.isEmpty(atoms)) {
					atoms.forEach(atom -> {
						try {
							atom.setUsername(
									SecurityFetcher.getInstance().decrypt(atom.getUsername())
							);
							atom.setPassword(
									SecurityFetcher.getInstance().decrypt(atom.getPassword())
							);
						} catch (Exception e) {
							throw new ContextedRuntimeException("Decrypt error. mixed datasource:" + mixedDataSourceName
									+ " ,repository:" + repository.getRepositoryName()
									+ " , atom:" + atom.getAtomName(), e);
						}
					});
				}
			});
		}

		return mixedDataSourceModel;
	}

	/**
	 * registry BeanFactoryPostProcessor for modify beanDefinition
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryBeanFactoryPostProcessor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = ClassUtils.getName(MixedDataBeanFactoryPostProcessor.class);
		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			BeanUtils.registryBean(beanName, beanDefinitionRegistry, MixedDataBeanFactoryPostProcessor.class);
		}
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

		// create DataSourceKey
		DataSourceKey dataSourceKey = new DataSourceKey();
		dataSourceKey.setMixDataName(mixedDataSourceName);
		dataSourceKey.setRepositoryMasterAtomDsMapper(repositoryMasterAtomDsMapper);
		dataSourceKey.setRepositorySlaveAtomDsMapper(repositorySlaveAtomDsMapper);

		// create DataSource Spring Bean, if only one repository, create MasterSlaveDataSource
		Class datasourceClazz = mixedDataSourceModel.getRepositories().size() == 1 ? MasterSlaveDataSource.class : RepositoryShardingDataSource.class;
		BeanUtils.registryBean(mixedDataSourceName, beanDefinitionRegistry, datasourceClazz, Arrays.asList(
				new PropertyValue("dataSourceKey", dataSourceKey),
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

	/**
	 * resitry RepositoryShardingAnnotationInterceptor
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryRepositoryShardingInterceptor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = "RepositoryShardingAnnotationInterceptor-PointcutAdvisor";
		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			log.info("Creating RepositoryShardingAnnotationInterceptor PointcutAdvisor bean definition, bean name:{}", beanName);

			RepositoryShardingAnnotationInterceptor interceptor = new RepositoryShardingAnnotationInterceptor();
			AspectJExpressionPointcut               pointcut    = new AspectJExpressionPointcut();
			pointcut.setExpression(REPOSITORY_SHARDING_POINTCUT_EXPRESSION);

			BeanUtils.registryBean(beanName, beanDefinitionRegistry, DefaultPointcutAdvisor.class, Arrays.asList(
					new PropertyValue("pointcut", pointcut),
					new PropertyValue("advice", interceptor),
					new PropertyValue("order", REPOSITORY_SHARDING_POINTCUT_ORDER)
			));
		}
	}

	/**
	 * registry MasterSlaveAnnotationInterceptor
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryMasterSlaveAnnotationInterceptor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = "MasterSlaveAnnotationInterceptor-PointcutAdvisor";
		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			log.info("Creating MasterSlaveAnnotationInterceptor PointcutAdvisor bean definition, bean name:{}", beanName);

			MasterSlaveAnnotationInterceptor interceptor = new MasterSlaveAnnotationInterceptor();
			AspectJExpressionPointcut        pointcut    = new AspectJExpressionPointcut();
			pointcut.setExpression(MASTERSLAVE_POINTCUT_EXPRESSION);

			BeanUtils.registryBean(beanName, beanDefinitionRegistry, DefaultPointcutAdvisor.class, Arrays.asList(
					new PropertyValue("pointcut", pointcut),
					new PropertyValue("advice", interceptor),
					new PropertyValue("order", MASTERSLAVE_POINTCUT_ORDER)
			));
		}
	}
}
