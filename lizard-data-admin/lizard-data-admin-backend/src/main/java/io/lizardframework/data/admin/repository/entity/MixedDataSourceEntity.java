package io.lizardframework.data.admin.repository.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class MixedDataSourceEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * mixed数据源名称
     */
    private String mixedName;

    /**
     * mixed数据源描述
     */
    private String mixedDesc;

    /**
     * 状态:online-可用 offline-不可用
     */
    private String state;

    /**
     * 数据源所属数据库类型
     */
    private String dbType;

    /**
     * 数据源配置信息json
     */
    private String configParam;

    /**
     * 负责人，多人时使用',' 分割
     */
    private String ownerUser;

    /**
     * 创建人
     */
    private String createUser;

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

    public String getMixedName() {
        return mixedName;
    }

    public void setMixedName(String mixedName) {
        this.mixedName = mixedName;
    }

    public String getMixedDesc() {
        return mixedDesc;
    }

    public void setMixedDesc(String mixedDesc) {
        this.mixedDesc = mixedDesc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getConfigParam() {
        return configParam;
    }

    public void setConfigParam(String configParam) {
        this.configParam = configParam;
    }

    public String getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(String ownerUser) {
        this.ownerUser = ownerUser;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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