package io.lizardframework.data.admin.repository.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class MixedDatasourceAtomEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 分库配置ID
     */
    private Long repositoryId;

    /**
     * 原子数据源名称
     */
    private String atomName;

    /**
     * 数据库配置ID
     */
    private Long databaseId;

    /**
     * 主从类型: master-主库 slave-从库 
     */
    private String masterSlaveType;

    /**
     * 状态: online-可用 offline-不可用
     */
    private String state;

    /**
     * 负载均衡权重
     */
    private Integer weight;

    /**
     * 数据源连接池类型:druid/HikariCP
     */
    private String datasourcePoolType;

    /**
     * 数据源连接池配置
     */
    private String poolConfig;

    /**
     * JDBC连接配置
     */
    private String jdbcParam;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepositoryId() {
        return repositoryId;
    }

    public void setRepositoryId(Long repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getAtomName() {
        return atomName;
    }

    public void setAtomName(String atomName) {
        this.atomName = atomName;
    }

    public Long getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(Long databaseId) {
        this.databaseId = databaseId;
    }

    public String getMasterSlaveType() {
        return masterSlaveType;
    }

    public void setMasterSlaveType(String masterSlaveType) {
        this.masterSlaveType = masterSlaveType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDatasourcePoolType() {
        return datasourcePoolType;
    }

    public void setDatasourcePoolType(String datasourcePoolType) {
        this.datasourcePoolType = datasourcePoolType;
    }

    public String getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(String poolConfig) {
        this.poolConfig = poolConfig;
    }

    public String getJdbcParam() {
        return jdbcParam;
    }

    public void setJdbcParam(String jdbcParam) {
        this.jdbcParam = jdbcParam;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}