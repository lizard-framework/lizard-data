package io.lizardframework.data.orm.plugin;

import io.lizardframework.data.orm.datasource.strategy.StrategyHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xueqi
 * @date 2020-09-29
 */
@Slf4j
public class SQLConvert {
	private static final Pattern SQL_PATTERN = Pattern.compile("\\$\\[.*?\\]\\$");

	public static String trim(String sql) {
		String targetSql = StringUtils.replace(sql, "\n", " ");
		targetSql = StringUtils.replace(targetSql, "\t", " ");
		targetSql = targetSql.replaceAll(" +", " ");
		return targetSql.trim();
	}

	public static String getTableShardingPlaceholder(String tableName) {
		return "\\$\\[" + tableName + "\\]\\$";
	}

	public static String getMatchTableShardingPlaceholder(String sql) {
		Matcher matcher = SQL_PATTERN.matcher(sql);
		while (matcher.find()) {
			String name = matcher.group();
			return name.substring(2, name.length() - 2);
		}
		return null;
	}

	public static String convertTableshardingSQL(String sql) {
		String placeholder = SQLConvert.getMatchTableShardingPlaceholder(sql);
		if (StringUtils.isEmpty(placeholder)) {
			log.debug("Table sharding placeholder is blank, not replace sql");
			return SQLConvert.trim(sql);
		}

		Map<String, String> shardingMapper = StrategyHolder.getTableShardingStrategy();
		if (CollectionUtils.isEmpty(shardingMapper)) {
			log.debug("Table sharding strategy is null, not replace sql");
			return SQLConvert.trim(sql);
		}

		// replace SQL table sharding placeholder
		String shardingSql = sql;
		for (Map.Entry<String, String> valueEntry : shardingMapper.entrySet()) {
			shardingSql = shardingSql.replaceAll(SQLConvert.getTableShardingPlaceholder(valueEntry.getKey()), valueEntry.getValue());
		}
		return SQLConvert.trim(shardingSql);
	}
}
