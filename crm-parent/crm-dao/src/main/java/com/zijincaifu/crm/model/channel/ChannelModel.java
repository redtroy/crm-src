package com.zijincaifu.crm.model.channel;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.entity.channel.ChannelEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;

public class ChannelModel extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7683567106040725618L;
    
    private ChannelEntity channel=new ChannelEntity();
    
    private PersonnelEntity personnel=new PersonnelEntity();

    public String getId()
    {
        return channel.getId();
    }

    public void setId(String id)
    {
        channel.setId(id);
    }

    public String getChannelId()
    {
        return channel.getChannelId();
    }

    public void setChannelId(String channelId)
    {
        channel.setChannelId(channelId);
    }

    public String getName()
    {
        return channel.getName();
    }

    public void setName(String name)
    {
        channel.setName(name);
    }

    public String getRemark()
    {
        return channel.getRemark();
    }

    public void setRemark(String remark)
    {
        channel.setRemark(remark);
    }

    public Date getStartTime()
    {
        return channel.getStartTime();
    }

    public void setStartTime(Date startTime)
    {
        channel.setStartTime(startTime);
    }

    public String getUid()
    {
        return channel.getUid();
    }

    public void setUid(String uid)
    {
        channel.setUid(uid);
    }

    public String getPersonnelName()
    {
        return personnel.getName();
    }

    public void setPersonnelName(String name)
    {
        personnel.setName(name);
    }
    
    
    
    
    
}
