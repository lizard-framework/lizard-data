package io.lizardframework.data.orm.spring.register;

import io.lizardframework.data.enums.LoadBalanceType;
import io.lizardframework.data.enums.MixedType;
import io.lizardframework.data.orm.Constants;
import io.lizardframework.data.orm.datasource.DataSourceKey;
import io.lizardframework.data.orm.datasource.MasterSlaveDataSource;
import io.lizardframework.data.orm.datasource.RepositoryShardingDataSource;
import io.lizardframework.data.orm.datasource.meta.DataSourceMBean;
import io.lizardframework.data.orm.interceptor.MasterSlaveAnnotationInterceptor;
import io.lizardframework.data.orm.interceptor.RepositoryShardingAnnotationInterceptor;
import io.lizardframework.data.orm.interceptor.TableShardingAnnotationInterceptor;
import io.lizardframework.data.orm.model.AtomDataSourceModel;
import io.lizardframework.data.orm.model.MixedDataSourceModel;
import io.lizardframework.data.orm.model.RepositoryDataSourceModel;
import io.lizardframework.data.orm.plugin.MyBatisTableShardingPlugin;
import io.lizardframework.data.orm.spring.register.extension.MixedDataBeanFactoryPostProcessor;
import io.lizardframework.data.orm.spring.register.extension.MixedDataSourceWarmupListener;
import io.lizardframework.data.orm.spring.register.meta.DataSourcePoolMBean;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import io.lizardframework.data.orm.spring.register.pool.DataSourcePoolRegisterFactory;
import io.lizardframework.data.orm.spring.register.pool.IDataSourcePoolRegister;
import io.lizardframework.data.orm.support.parser.MixedDataSourceModelParser;
import io.lizardframework.data.remoting.impl.MixedConfigFetcher;
import io.lizardframework.data.utils.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.util.ClassUtils;

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
	 * @param mixedDataSourceRegisterMBean
	 * @param beanDefinitionRegistry
	 */
	public void doRegistry(MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean, BeanDefinitionRegistry beanDefinitionRegistry) throws Exception {
		String mixedDataSourceName = mixedDataSourceRegisterMBean.getMixedDataSourceName();

		if (!beanDefinitionRegistry.containsBeanDefinition(mixedDataSourceName)) {

			// get mixed-data config
			MixedDataSourceModel mixedDataSourceModel = this.fetchAndConvertModel(mixedDataSourceRegisterMBean);

			// registry mixed datasource bean
			this.registryMixDataSourceBean(mixedDataSourceModel, beanDefinitionRegistry);

			// registry repository sharding interceptor
			this.registryRepositoryShardingInterceptor(beanDefinitionRegistry);

			// registry master slave interceptor
			this.registryMasterSlaveAnnotationInterceptor(beanDefinitionRegistry);

			// registry table sharding interceptor
			this.registryTableShardingAnnotationInterceptor(beanDefinitionRegistry);

			// register mybatis table sharding plugin
			this.registryMybatisTableShardingPlugin(beanDefinitionRegistry);

			// registry BeanFactoryPostPorcessor for modify bean definition
			this.registryBeanFactoryPostProcessor(mixedDataSourceRegisterMBean, beanDefinitionRegistry);

			// registry warm up datasource application listener
			this.registryWarmupApplicationListener(mixedDataSourceRegisterMBean, beanDefinitionRegistry);

			// report framework version and metric info
		} else {
			log.warn("Mixed datasource bean:{} already definition.", mixedDataSourceName);
		}
	}

	/**
	 * fetch mixed-data-source config and convert to MixedDataSourceModel model
	 *
	 * @param mixedDataSourceRegisterMBean
	 * @return
	 */
	private MixedDataSourceModel fetchAndConvertModel(MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean) throws Exception {
		return MixedDataSourceModelParser.parse(mixedDataSourceRegisterMBean);
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
		Map<String, LoadBalanceType>       repositoryLoadBalanceMapper  = new HashMap<>();

		// registry atom datasource pool in spring context
		this.registryAtomDataSource(mixedDataSourceModel, beanDefinitionRegistry, dataSources, repositoryMasterAtomDsMapper,
				repositorySlaveAtomDsMapper, repositoryLoadBalanceMapper);

		// create DataSourceKey
		DataSourceKey dataSourceKey = new DataSourceKey(mixedDataSourceName, repositoryMasterAtomDsMapper, repositorySlaveAtomDsMapper, repositoryLoadBalanceMapper);

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
	 * @param repositoryLoadBalanceMapper
	 */
	private void registryAtomDataSource(MixedDataSourceModel mixedDataSourceModel,
	                                    BeanDefinitionRegistry beanDefinitionRegistry,
	                                    Map<String, RuntimeBeanReference> dataSources,
	                                    Map<String, List<DataSourceMBean>> repositoryMasterAtomDsMapper,
	                                    Map<String, List<DataSourceMBean>> repositorySlaveAtomDsMapper,
	                                    Map<String, LoadBalanceType> repositoryLoadBalanceMapper) {
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

			// save repository loadbalance mapper
			repositoryLoadBalanceMapper.put(repositoryName, repository.getLoadBalance());
		}
	}

	/**
	 * resitry RepositoryShardingAnnotationInterceptor
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryRepositoryShardingInterceptor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = REPOSITORY_SHARDING_POINTCUT_ADVISOR_BEAN;

		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			log.info("Creating RepositoryShardingAnnotationInterceptor PointcutAdvisor bean definition, bean name:{}", beanName);

			BeanUtils.registryBean(REPOSITORY_SHARDING_INTERCEPTOR_BEAN, beanDefinitionRegistry, RepositoryShardingAnnotationInterceptor.class);

			BeanUtils.registryPointcutAdvisorBean(beanName, beanDefinitionRegistry, REPOSITORY_SHARDING_POINTCUT_EXPRESSION,
					new RuntimeBeanReference(REPOSITORY_SHARDING_INTERCEPTOR_BEAN), REPOSITORY_SHARDING_POINTCUT_ORDER);
		}
	}

	/**
	 * registry MasterSlaveAnnotationInterceptor
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryMasterSlaveAnnotationInterceptor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = MASTER_SLAVE_POINTCUT_ADVISOR_BEAN;

		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			log.info("Creating MasterSlaveAnnotationInterceptor PointcutAdvisor bean definition, bean name:{}", beanName);

			BeanUtils.registryBean(MASTER_SLAVE_INTERCEPTOR_BEAN, beanDefinitionRegistry, MasterSlaveAnnotationInterceptor.class);

			BeanUtils.registryPointcutAdvisorBean(beanName, beanDefinitionRegistry, MASTERSLAVE_POINTCUT_EXPRESSION,
					new RuntimeBeanReference(MASTER_SLAVE_INTERCEPTOR_BEAN), MASTERSLAVE_POINTCUT_ORDER);
		}
	}

	/**
	 * registry TableShardingAnnotationInterceptor
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryTableShardingAnnotationInterceptor(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = TABLE_SHARDING_POINTCUT_ADVISOR_BEAN;

		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			log.info("Creating TableShardingAnnotationInterceptor PointcutAdvisor bean definition, bean name:{}", beanName);

			BeanUtils.registryBean(TABLE_SHARDING_INTERCEPTOR_BEAN, beanDefinitionRegistry, TableShardingAnnotationInterceptor.class);

			BeanUtils.registryPointcutAdvisorBean(beanName, beanDefinitionRegistry, TABLE_SHARDING_POINTCUT_EXPRESSION,
					new RuntimeBeanReference(TABLE_SHARDING_INTERCEPTOR_BEAN), TABLE_SHARDING_POINTCUT_ORDER);
		}
	}

	/**
	 * registry MyBatisTableShardingPlugin
	 *
	 * @param beanDefinitionRegistry
	 */
	private void registryMybatisTableShardingPlugin(BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = MYBATIS_TABLE_SHARDING_PLUGIN_BEAN;

		// mybatis class must be in classloader
		if (ClassUtils.isPresent(MYBATIS_Param_ANNOTATION_CLASS, null)) {

			if (!beanDefinitionRegistry.containsBeanDefinition(beanName))
				BeanUtils.registryBean(beanName, beanDefinitionRegistry, MyBatisTableShardingPlugin.class);
		}
	}

	/**
	 * registry BeanFactoryPostProcessor for modify beanDefinition
	 *
	 * @param mixedDataSourceRegisterMBean
	 * @param beanDefinitionRegistry
	 */
	private void registryBeanFactoryPostProcessor(MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean, BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = MIXED_DATA_BEANFACTORY_POST_PROCESSOR_BEAN;
		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList = new ArrayList<>();
			mixedDataSourceRegisterMBeanList.add(mixedDataSourceRegisterMBean);
			BeanUtils.registryBean(beanName, beanDefinitionRegistry, MixedDataBeanFactoryPostProcessor.class, Arrays.asList(
					new PropertyValue("mixedDataSourceRegisterMBeanList", mixedDataSourceRegisterMBeanList)
			));
		} else {
			BeanDefinition                     beanDefinition                   = beanDefinitionRegistry.getBeanDefinition(beanName);
			List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList = (List<MixedDataSourceRegisterMBean>) beanDefinition.getPropertyValues().get("mixedDataSourceRegisterMBeanList");
			mixedDataSourceRegisterMBeanList.add(mixedDataSourceRegisterMBean);
		}
	}

	/**
	 * registry warm up datasource application listener
	 *
	 * @param mixedDataSourceRegisterMBean
	 * @param beanDefinitionRegistry
	 */
	private void registryWarmupApplicationListener(MixedDataSourceRegisterMBean mixedDataSourceRegisterMBean, BeanDefinitionRegistry beanDefinitionRegistry) {
		String beanName = WARM_UP_DATASOURCE_LISTENER;
		if (!beanDefinitionRegistry.containsBeanDefinition(beanName)) {
			List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList = new ArrayList<>();
			mixedDataSourceRegisterMBeanList.add(mixedDataSourceRegisterMBean);
			BeanUtils.registryBean(beanName, beanDefinitionRegistry, MixedDataSourceWarmupListener.class, Arrays.asList(
					new PropertyValue("mixedDataSourceRegisterMBeanList", mixedDataSourceRegisterMBeanList)
			));
		} else {
			BeanDefinition                     beanDefinition                   = beanDefinitionRegistry.getBeanDefinition(beanName);
			List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList = (List<MixedDataSourceRegisterMBean>) beanDefinition.getPropertyValues().get("mixedDataSourceRegisterMBeanList");
			mixedDataSourceRegisterMBeanList.add(mixedDataSourceRegisterMBean);
		}
	}
}
