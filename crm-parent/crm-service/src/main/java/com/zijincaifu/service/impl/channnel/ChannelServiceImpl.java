package com.zijincaifu.service.impl.channnel;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.CustomDecimal;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.channel.IChannelDao;
import com.zijincaifu.crm.entity.channel.ChannelEntity;
import com.zijincaifu.crm.model.channel.ChannelModel;
import com.zijincaifu.model.channel.ChannelQuery;
import com.zijincaifu.service.channel.IChannelService;
import com.zijincaifu.service.rfid.IRfidKeyService;

@Service
@Transactional
public class ChannelServiceImpl implements IChannelService
{
    @Autowired
    private IChannelDao channelDao;
    
    @Autowired
    private IRfidKeyService keyService;
    
    @Override
    public List<ChannelModel> queryChannels(ChannelQuery query)
    {
        try
        {
            QueryCondition<ChannelEntity> condition = new QueryCondition<ChannelEntity>();
            if (query == null)
            {
                return null;
            }
            condition.addAllCondition(query);
            condition.setPage(query);
            List<ChannelModel> list = channelDao.queryChannel(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询渠道信息错误", e);
        }
    }
    
    @Override
    public void addChannel(ChannelEntity channel)
    {
        try
        {
            Long lastkey = keyService.getKey(1);
            String rfidNo = CustomDecimal.getDecimalString(3,
                    1000,
                    new BigDecimal(lastkey));
            channel.setChannelId(rfidNo);
            channelDao.addChannel(channel);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增渠道信息错误", e);
        }
    }
    
    @Override
    public ChannelModel getChannel(String channelId)
    {
        return channelDao.getChannel(channelId);
    }
    
    @Override
    public void editChannel(ChannelEntity channel)
    {
        channelDao.updateChannel(channel);
    }
    
    @Override
    public void deleteChannel(String id)
    {
        channelDao.deleteChannel(id);
    }
    
}
