package io.lizardframework.data.admin.repository.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class ResourcesDatabaseEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 数据库类型：mysql,oracle
     */
    private String dbType;

    /**
     * 数据库实例(schema)名称
     */
    private String dbName;

    /**
     * 数据库实例连接host地址
     */
    private String dbHost;

    /**
     * 数据库实例端口
     */
    private Integer dbPort;

    /**
     * 数据库连接用户名
     */
    private String dbUsername;

    /**
     * 数据库连接密码
     */
    private String dbPassword;

    /**
     * 负责人
     */
    private String ownerName;

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

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbHost() {
        return dbHost;
    }

    public void setDbHost(String dbHost) {
        this.dbHost = dbHost;
    }

    public Integer getDbPort() {
        return dbPort;
    }

    public void setDbPort(Integer dbPort) {
        this.dbPort = dbPort;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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