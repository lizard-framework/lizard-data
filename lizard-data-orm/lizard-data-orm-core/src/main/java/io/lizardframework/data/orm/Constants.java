package io.lizardframework.data.orm;

import io.lizardframework.data.CommonConstants;
import io.lizardframework.data.orm.interceptor.MasterSlaveAnnotationInterceptor;
import io.lizardframework.data.orm.interceptor.RepositoryShardingAnnotationInterceptor;
import io.lizardframework.data.orm.interceptor.TableShardingAnnotationInterceptor;
import io.lizardframework.data.orm.plugin.MyBatisTableShardingPlugin;
import io.lizardframework.data.orm.spring.register.extension.MixedDataBeanFactoryPostProcessor;
import io.lizardframework.data.orm.spring.register.extension.MixedDataSourceWarmupListener;
import org.springframework.util.ClassUtils;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public interface Constants extends CommonConstants {

	// ------- bean name ------- //
	String MASTER_SLAVE_POINTCUT_ADVISOR_BEAN        = "MasterSlaveAnnotationInterceptor-PointcutAdvisor";
	String REPOSITORY_SHARDING_POINTCUT_ADVISOR_BEAN = "RepositoryShardingAnnotationInterceptor-PointcutAdvisor";
	String TABLE_SHARDING_POINTCUT_ADVISOR_BEAN      = "TableShardingAnnotationInterceptor-PointcutAdvisor";

	String MASTER_SLAVE_INTERCEPTOR_BEAN        = ClassUtils.getQualifiedName(MasterSlaveAnnotationInterceptor.class);
	String REPOSITORY_SHARDING_INTERCEPTOR_BEAN = ClassUtils.getQualifiedName(RepositoryShardingAnnotationInterceptor.class);
	String TABLE_SHARDING_INTERCEPTOR_BEAN      = ClassUtils.getQualifiedName(TableShardingAnnotationInterceptor.class);

	String MYBATIS_TABLE_SHARDING_PLUGIN_BEAN         = ClassUtils.getQualifiedName(MyBatisTableShardingPlugin.class);
	String MIXED_DATA_BEANFACTORY_POST_PROCESSOR_BEAN = ClassUtils.getQualifiedName(MixedDataBeanFactoryPostProcessor.class);
	String WARM_UP_DATASOURCE_LISTENER                = ClassUtils.getQualifiedName(MixedDataSourceWarmupListener.class);

	// ------- spring ------ //
	String REPOSITORY_SHARDING_POINTCUT_EXPRESSION = "@annotation(io.lizardframework.data.orm.annotation.RepositorySharding)";
	String MASTERSLAVE_POINTCUT_EXPRESSION         = "@annotation(io.lizardframework.data.orm.annotation.MasterSlave)";
	String TABLE_SHARDING_POINTCUT_EXPRESSION      = "@annotation(io.lizardframework.data.orm.annotation.TableSharding)";

	int REPOSITORY_SHARDING_POINTCUT_ORDER = 100;
	int MASTERSLAVE_POINTCUT_ORDER         = 200;
	int TRANSACTION_ADVISOR_ORDER          = 300;
	int TABLE_SHARDING_POINTCUT_ORDER      = 100;

	// ------- mybatis ------- //
	String MYBATIS_Param_ANNOTATION_CLASS = "org.apache.ibatis.annotations.Param";

	// ------- config key ------- //
	String MIXED_DATA_NAMES_KEY                           = "lizard.data.orm.mixed.names";
	String MIXED_DATA_mybatisSqlSessionFactory_KEY_FORMAT = "lizard.data.orm.%s.mybatisSqlSessionFactory";

	// ------- spring customer label attribute name ------- //
	String ATTRIBUTE_MIXED_NAME                     = "mixed-name";
	String ATTRIBUTE_MYBATIS_SQLSESSIONFACTORY_BEAN = "mybatis-sqlsession-factory";
}
