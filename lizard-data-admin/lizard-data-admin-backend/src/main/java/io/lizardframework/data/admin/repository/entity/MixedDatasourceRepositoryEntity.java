package io.lizardframework.data.admin.repository.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class MixedDatasourceRepositoryEntity implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 数据源配置表ID
     */
    private Long datasourceId;

    /**
     * 分库名称
     */
    private String repositoryName;

    /**
     * 状态: online-可用 offline-不可用
     */
    private String state;

    /**
     * 负载均衡策略:  random-随机 roundRobin-轮询
     */
    private String loadBalance;

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

    public Long getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(Long datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
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