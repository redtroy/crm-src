package com.zijincaifu.model.product;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ProductQuery extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7772382060727204709L;
    
    private String id;
    
    private String productId;
    
    private String name;
    
    private String startDate;
    
    private String endDate;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProductId()
    {
        return productId;
    }

    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
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
    
    
}
