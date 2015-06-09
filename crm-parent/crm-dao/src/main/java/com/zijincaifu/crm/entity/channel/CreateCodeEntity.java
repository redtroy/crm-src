package com.zijincaifu.crm.entity.channel;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class CreateCodeEntity extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -664923564705405853L;
    
    private String productid;
    
    private String channelid;
    
    private String employeeid;
    
    private String url;

    public String getProductid()
    {
        return productid;
    }

    public void setProductid(String productid)
    {
        this.productid = productid;
    }

    public String getChannelid()
    {
        return channelid;
    }

    public void setChannelid(String channelid)
    {
        this.channelid = channelid;
    }

    public String getEmployeeid()
    {
        return employeeid;
    }

    public void setEmployeeid(String employeeid)
    {
        this.employeeid = employeeid;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    
}
