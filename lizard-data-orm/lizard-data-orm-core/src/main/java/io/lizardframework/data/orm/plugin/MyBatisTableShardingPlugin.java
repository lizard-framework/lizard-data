package io.lizardframework.data.orm.plugin;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Properties;

/**
 * Mybatis Table Sharding plugin
 *
 * @author xueqi
 * @date 2020-09-29
 */
// inteceptor: StatementHandler.prepare(Connection, Integer) method
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class MyBatisTableShardingPlugin implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		log.debug("Enter into MyBatisTableShardingPlugin, invocation:{}", invocation.toString());

		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		String           originalSql      = SQLConvert.trim(statementHandler.getBoundSql().getSql());
		log.debug("Original sql:{}", originalSql);

		String targetSql = SQLConvert.convertTableshardingSQL(originalSql);
		log.debug("Converted sql:{}", targetSql);

		if (!StringUtils.equals(originalSql, targetSql)) {
			Field field = BoundSql.class.getDeclaredField("sql");
			ReflectionUtils.makeAccessible(field);
			ReflectionUtils.setField(field, statementHandler.getBoundSql(), targetSql);
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}


}
