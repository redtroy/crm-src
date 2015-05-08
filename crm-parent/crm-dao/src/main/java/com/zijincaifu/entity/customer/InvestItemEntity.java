package com.zijincaifu.entity.customer;

import java.io.Serializable;
import java.sql.Date;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 客户投资记录
 * @author dujinxin
 *
 */
public class InvestItemEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5347655985185246417L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 客户编号
     */
    private String customerId;
    
    /**
     * 产品编号
     */
    private String productId;
    
    /**
     * 渠道编号
     */
    private String channelId;
    
    /**
     * 状态
     */
    private Integer state;
    
    /**
     * 金额
     */
    private Double amount;
    
    /**
     * 注册日期
     */
    private Date registTime;
    
    /**
     * 投资日期
     */
    private Date investTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}

	public Date getInvestTime() {
		return investTime;
	}

	public void setInvestTime(Date investTime) {
		this.investTime = investTime;
	}
    
}
