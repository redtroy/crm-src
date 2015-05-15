package com.zijincaifu.service.channel;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.channel.ChannelEntity;
import com.zijincaifu.crm.model.channel.ChannelModel;
import com.zijincaifu.model.channel.ChannelQuery;

public interface IChannelService
{

    /**
     * @param channel
     * @throws ServiceException
     */
    
    public List<ChannelModel> queryChannels(ChannelQuery query);

    public void addChannel(ChannelEntity channel);
}
