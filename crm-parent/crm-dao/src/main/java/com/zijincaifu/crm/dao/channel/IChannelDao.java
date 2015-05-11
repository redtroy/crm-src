package com.zijincaifu.crm.dao.channel;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.channel.ChannelEntity;

public interface IChannelDao {
	
	/**
	 * 新增渠道
	 * @param channel
	 */
	@Insert
	public void addChannel(ChannelEntity channel);
	
	/**
	 * 修改渠道
	 * @param channel 
	 */
	@Update
	public void updateChannel(ChannelEntity channel);
	
	/**
	 * 删除渠道
	 * @param id
	 */
	@Delete
	public void deleteChannel(String id);
	
	/**
	 * 获取渠道信息
	 * @param channelId
	 */
	public ChannelEntity getChannel(String channelId);
	
	/**
	 * 查看渠道
	 * @param query
	 */
	public List<ChannelEntity> queryChannel(QueryCondition<ChannelEntity> query);

}
