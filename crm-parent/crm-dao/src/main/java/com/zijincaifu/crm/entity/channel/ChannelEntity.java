package com.zijincaifu.crm.entity.channel;

import java.io.Serializable;
import java.sql.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.channel.IChannelDao;

/**
 * 渠道信息
 * @author Administrator
 *
 */
@Entity(mapper=IChannelDao.class)
@Table (name="CRM_CHANNEL")
public class ChannelEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -450134536501872240L;
	
	/**
	 * 主键
	 */
	@Id(column="ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	/**
	 * 渠道编号
	 */
	@Column(name="CHANNELID")
	private String channelId;
	
	/**
	 * 渠道名称
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * 渠道描述
	 */
	@Column(name="DESCRIBE")
	private String describe;
	
	/**
	 * 推广起始时间
	 */
	@Column(name="STARTTIME")
	private Date startTime;
	
	/**
	 * 创建该渠道的员工编号
	 */
	@Column(name="UID")
	private String uid;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public String getUID() {
		return uid;
	}

	public void setUID(String uid) {
		this.uid = uid;
	}
	

}
