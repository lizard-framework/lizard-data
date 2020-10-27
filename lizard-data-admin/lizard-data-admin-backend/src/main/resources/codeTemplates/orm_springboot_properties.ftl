# 当应用中使用多个数据源时，请使用 ',' 分割
lizard.data.orm.mixed.names=${MixedDataSourceName}
lizard.data.orm.${MixedDataSourceName}.mybatisSqlSessionFactory=${MixedDataSourceName}SqlSessionFactoryBean

# 请使用@Configuration自行创建MyBatisSqlSessionFactoryBean和MapperScanner
# 请使用@Configuration自行创建DataSourceTransactionManager
# 创建时，DataSource指定bean name为：${MixedDataSourceName}