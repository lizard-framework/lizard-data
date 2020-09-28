package io.lizardframework.data.orm;

/**
 * @author xueqi
 * @date 2020-09-28
 */
public interface Constants {

	// ------- spring ------//
	String REPOSITORY_SHARDING_POINTCUT_EXPRESSION = "@annotation(io.lizardframework.data.orm.annotation.RepositorySharding)";
	String MASTERSLAVE_POINTCUT_EXPRESSION         = "@annotation(io.lizardframework.data.orm.annotation.MasterSlave)";
	int    REPOSITORY_SHARDING_POINTCUT_ORDER      = 100;
	int    MASTERSLAVE_POINTCUT_ORDER              = 200;
	int    TRANSACTION_ADVISOR_ORDER               = 300;
}
