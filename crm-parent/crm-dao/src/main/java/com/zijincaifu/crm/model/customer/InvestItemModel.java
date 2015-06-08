package com.zijincaifu.crm.model.customer;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;

public class InvestItemModel extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -4732747458959271487L;
    
    private InvestItemEntity investItem = new InvestItemEntity();
    
    private ProductEntity product = new ProductEntity();
    
    public String getId()
    {
        return investItem.getId();
    }
    
    public void setId(String id)
    {
        investItem.setId(id);
    }
    
    public String getCustomerId()
    {
        return investItem.getCustomerId();
    }
    
    public void setCustomerId(String customerId)
    {
        investItem.setCustomerId(customerId);
    }
    
    public String getProductId()
    {
        return investItem.getProductId();
    }
    
    public void setProductId(String productId)
    {
        investItem.setProductId(productId);
    }
    
    public String getChannelId()
    {
        return investItem.getChannelId();
    }
    
    public void setChannelId(String channelId)
    {
        investItem.setChannelId(channelId);
    }
    
    public InvestItemStateEnum getState()
    {
        return investItem.getState();
    }
    
    public void setState(InvestItemStateEnum state)
    {
        investItem.setState(state);
    }
    
    public Double getAmount()
    {
        return investItem.getAmount();
    }
    
    public void setAmount(Double amount)
    {
        investItem.setAmount(amount);
    }
    
    public Date getRegistTime()
    {
        return investItem.getRegistTime();
    }
    
    public void setRegistTime(Date registTime)
    {
        investItem.setRegistTime(registTime);
    }
    
    public Date getInvestTime()
    {
        return investItem.getInvestTime();
    }
    
    public void setInvestTime(Date investTime)
    {
        investItem.setInvestTime(investTime);
    }
    
    public String getName()
    {
        return product.getName();
    }
    
    public void setName(String name)
    {
        product.setName(name);
    }
    
    public String getEmployeId()
    {
        return investItem.getEmployeId();
    }
    
    public void setEmployeId(String employeId)
    {
        investItem.setEmployeId(employeId);
    }
    
}
