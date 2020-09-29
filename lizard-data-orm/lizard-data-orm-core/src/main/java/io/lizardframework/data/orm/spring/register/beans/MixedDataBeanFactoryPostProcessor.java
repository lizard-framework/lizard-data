package io.lizardframework.data.orm.spring.register.beans;

import io.lizardframework.data.orm.Constants;
import io.lizardframework.data.orm.spring.register.meta.MixedDataSourceRegisterMBean;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.transaction.config.TransactionManagementConfigUtils;

import java.util.List;

/**
 * @author xueqi
 * @date 2020-09-28
 */
@Slf4j
public class MixedDataBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Constants {

	@Setter
	private List<MixedDataSourceRegisterMBean> mixedDataSourceRegisterMBeanList;

	// modify BeanDefinition
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.modifyBeanFactoryTransactionAttributeSourceAdvisor(beanFactory);

		this.injectMybatisTableShardingPlugin(beanFactory);
	}

	/**
	 * modify BeanFactoryTransactionAttributeSourceAdvisor, set order value
	 *
	 * @param beanFactory
	 */
	private void modifyBeanFactoryTransactionAttributeSourceAdvisor(ConfigurableListableBeanFactory beanFactory) {
		String beanName = TransactionManagementConfigUtils.TRANSACTION_ADVISOR_BEAN_NAME;

		BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
		if (beanDefinition != null) {
			log.info("Modify BeanFactoryTransactionAttributeSourceAdvisor order value to: {}", TRANSACTION_ADVISOR_ORDER);

			beanDefinition.getPropertyValues().add("order", TRANSACTION_ADVISOR_ORDER);
		}
	}

	/**
	 * inject MybatisTableShardingPlugin to SqlSessionFactory
	 *
	 * @param beanFactory
	 */
	private void injectMybatisTableShardingPlugin(ConfigurableListableBeanFactory beanFactory) {
		// MYBATIS_TABLE_SHARDING_PLUGIN_BEAN BeanDefinition must be exist
		if (beanFactory.containsBeanDefinition(MYBATIS_TABLE_SHARDING_PLUGIN_BEAN)) {

			// each mbean mybatisSqlSessionFactory inject plugin
			for (MixedDataSourceRegisterMBean mbean : mixedDataSourceRegisterMBeanList) {

				if (!StringUtils.isEmpty(mbean.getMybatisSqlSessionFactory())) {
					if (beanFactory.containsBeanDefinition(mbean.getMybatisSqlSessionFactory())) {
						BeanDefinition sqlSessionFactoryBean = beanFactory.getBeanDefinition(mbean.getMybatisSqlSessionFactory());
						ManagedArray   plugins               = (ManagedArray) sqlSessionFactoryBean.getPropertyValues().get("plugins");

						if (plugins == null) {
							plugins = new ManagedArray(Interceptor.class.getName(), 1);
							sqlSessionFactoryBean.getPropertyValues().add("plugins", plugins);
						}

						plugins.add(0, new RuntimeBeanReference(MYBATIS_TABLE_SHARDING_PLUGIN_BEAN));
						log.info("Inject MybatisTableShardingPlugin to mybatis sqlSessionFactory:{}", mbean.getMybatisSqlSessionFactory());
					}

				}
			}
		}
	}
}
