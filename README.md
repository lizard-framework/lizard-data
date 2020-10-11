# lizard-data

## lizard-data-orm

### 场景验证结果

**单库无读写分离场景**

|场景|验证结果|说明|
|----|----|----|
|单条更新场景，没有@Transactional注解|通过| |
|单条更新场景，使用@Transactional注解|通过| |
|单条更新场景，操作完成抛出异常，使用@Transactional注解|通过| |
|多条更新,事务嵌套|通过| |

### 性能验证

## lizard-data-cache