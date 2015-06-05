package com.zijincaifu.model.channel;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class ChannelQuery extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5036757425223455664L;
    
    private String id;
    
    private String channelId;
    
    private String name;
    
    private String remark;
    
    private String startTime;
    
    private String endTime;
    
    private String uid;
    
    private String defaultUid;
    
    private String companyStr;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getChannelId()
    {
        return channelId;
    }
    
    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(String startTime)
    {
        this.startTime = startTime;
    }
    
    public String getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(String endTime)
    {
        this.endTime = endTime;
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public String getDefaultUid()
    {
        return defaultUid;
    }
    
    public void setDefaultUid(String defaultUid)
    {
        this.defaultUid = defaultUid;
    }
    
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    
    public String getCompanyStr()
    {
        return companyStr;
    }
    
    public void setCompanyStr(String companyStr)
    {
        this.companyStr = companyStr;
    }
    
}
