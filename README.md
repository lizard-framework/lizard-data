# lizard-data

Lizard Data是一个提供数据访问的抽象与简化的框架。

## 关系型数据库

分库场景数据源关系
```
+------------------------------+         +---------------------+
|                              |         |                     |
+ RepositoryShardingDataSource + 1 --> N + ReadWriteDataSource +
|                              |         |                     |
+------------------------------+         +-------------------- +

Mix-Data 1 --> 1 RepositoryShardingDataSource 1 --> N Data-Group

Data-Group 1 --> 1 ReadWriteDataSource

ReadWriteDataSource 1 --> N DataSource(write/read)

一个Mix-Data对应一个RepositoryShardingDataSource数据源，里面维护每个分库的读写数据源ReadWriteDataSource
一个ReadWriteDataSource维护着一个数据库的多个实际读写数据源的DataSource
```

## 缓存数据库

**JdbcTemplate方法调用梳理**
```java
public class JdbcTemplate extends JdbcAccessor implements JdbcOperations {
    
    // query方法底层调用execute()
    @Override
	@Nullable
	public <T> T query(final String sql, final ResultSetExtractor<T> rse) throws DataAccessException {
		Assert.notNull(sql, "SQL must not be null");
		Assert.notNull(rse, "ResultSetExtractor must not be null");
		if (logger.isDebugEnabled()) {
			logger.debug("Executing SQL query [" + sql + "]");
		}

		/**
		 * Callback to execute the query.
		 */
		class QueryStatementCallback implements StatementCallback<T>, SqlProvider {
			@Override
			@Nullable
			public T doInStatement(Statement stmt) throws SQLException {
				ResultSet rs = null;
				try {
					rs = stmt.executeQuery(sql);
					return rse.extractData(rs);
				}
				finally {
					JdbcUtils.closeResultSet(rs);
				}
			}
			@Override
			public String getSql() {
				return sql;
			}
		}

		return execute(new QueryStatementCallback());
	}
}
```