package io.lizardframework.data.orm.spring.register.beans;

import io.lizardframework.data.orm.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.transaction.config.TransactionManagementConfigUtils;

/**
 * @author xueqi
 * @date 2020-09-28
 */
@Slf4j
public class MixedDataBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Constants {

	// modify BeanDefinition
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		this.modifyBeanFactoryTransactionAttributeSourceAdvisor(beanFactory);
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
}
