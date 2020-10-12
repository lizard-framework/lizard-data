package io.lizardframework.data.orm.fixture.sharding.repository.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 * Order transaction table
 */
public class TransactionEntity implements Serializable {
    /**
     * primary key
     */
    private Long id;

    /**
     * order no
     */
    private String orderNo;

    /**
     * transaction no
     */
    private String txNo;

    /**
     * transaction amount
     */
    private BigDecimal amount;

    /**
     * create time
     */
    private Date createTime;

    /**
     * update time
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTxNo() {
        return txNo;
    }

    public void setTxNo(String txNo) {
        this.txNo = txNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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