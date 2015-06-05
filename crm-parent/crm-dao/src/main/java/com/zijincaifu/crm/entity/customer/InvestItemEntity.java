package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.customer.IInvestItemDao;
import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;

/**
 * 客户投资记录
 * @author dujinxin
 *
 */
@Entity(mapper = IInvestItemDao.class)
@Table(name = "CRM_INVEST_ITEM")
public class InvestItemEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5347655985185246417L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 客户编号
     */
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    
    /**
     * 产品编号
     */
    @Column(name = "PRODUCT_ID")
    private String productId;
    
    /**
     * 渠道编号
     */
    @Column(name = "CHANNEL_ID")
    private String channelId;
    
    /**
     * 状态
     */
    @Column(name = "STATE")
    private InvestItemStateEnum state;
    
    /**
     * 金额
     */
    @Column(name = "AMOUNT")
    private Double amount;
    
    /**
     * 注册日期
     */
    @Column(name = "REGIST_TIME")
    private Date registTime;
    
    /**
     * 投资日期
     */
    @Column(name = "INVEST_TIME")
    private Date investTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getChannelId()
    {
        return channelId;
    }
    
    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
    
    public InvestItemStateEnum getState()
    {
        return state;
    }
    
    public void setState(InvestItemStateEnum state)
    {
        this.state = state;
    }
    
    public Double getAmount()
    {
        return amount;
    }
    
    public void setAmount(Double amount)
    {
        this.amount = amount;
    }
    
    public Date getRegistTime()
    {
        return registTime;
    }
    
    public void setRegistTime(Date registTime)
    {
        this.registTime = registTime;
    }
    
    public Date getInvestTime()
    {
        if (!getState().equals(InvestItemStateEnum.INVEST))
        {
            return null;
        }
        return investTime;
    }
    
    public void setInvestTime(Date investTime)
    {
        this.investTime = investTime;
    }
    
}
