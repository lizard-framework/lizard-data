<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:lizard-data-orm="http://www.lizardframework.io/schema/lizard-data-orm"
       xmlns:tx="http://www.springframework.org/schema/tx" xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.lizardframework.io/schema/lizard-data-orm http://www.lizardframework.io/schema/lizard-data-orm.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd http://www.springframework.org/schema/aop https://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd"
       default-lazy-init="false">

    <!-- annotation -->
    <context:annotation-config/>
    <context:component-scan base-package="com.xxx.xxx"/>

    <!-- open @Transactional -->
    <tx:annotation-driven/>

    <!-- mixed datasource: ${MixedDataSourceName} -->
    <lizard-data-orm:mixed-datasource mixed-name="${MixedDataSourceName}"
                                      mybatis-sqlsession-factory="${MixedDataSourceName}SqlSessionFactoryBean"/>
    <!-- transaction manager -->
    <bean id="${MixedDataSourceName}Tx" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="${MixedDataSourceName}"/>
    </bean>
    <!-- mybatis sqlsession factory -->
    <bean id="${MixedDataSourceName}SqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="${MixedDataSourceName}"/>
        <!-- mapperLocations根据实际情况修改 -->
        <property name="mapperLocations" value="classpath*:mappers/simple/*Mapper.xml"/>
    </bean>
    <bean id="${MixedDataSourceName}MapperScanner" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="io.lizardframework.data.orm.fixture.simple.repository"/>
        <property name="sqlSessionFactoryBeanName" value="${MixedDataSourceName}SqlSessionFactoryBean"/>
        <property name="annotationClass" value="org.springframework.stereotype.Repository"/>
    </bean>

</beans>