package io.lizardframework.data.admin.support.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xueqi
 * @date 2020-11-14
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "io.lizardframework.data.admin.repository", annotationClass = Repository.class)
public class MyBatisConfiguration {
}
