package com.zijincaifu.model.customer;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class CustomerQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2686333218450272747L;
    
    private String customerId;
    
    private String name;
    
    private String phone;
    
    private String productId;
    
    private String channelId;
    
    private Integer level;
    
    private String startDate;
    
    private String endDate;
    
    private String unionId;
    
    private String employeId;
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
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
    
    public Integer getLevel()
    {
        return level;
    }
    
    public void setLevel(Integer level)
    {
        this.level = level;
    }
    
    public String getStartDate()
    {
        return startDate;
    }
    
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }
    
    public String getEndDate()
    {
        return endDate;
    }
    
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public String getEmployeId()
    {
        return employeId;
    }
    
    public void setEmployeId(String employeId)
    {
        this.employeId = employeId;
    }
    
}
