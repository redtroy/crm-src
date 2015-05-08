package com.zijincaifu.entity.customer;

import java.io.Serializable;
import java.sql.Date;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 客户实体
 * @author dujinxin
 *
 */
public class CustomerEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1314689595689972219L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 客户编码
     */
    private String customerId;
    
    /**
     * 客户姓名
     */
    private String name;
    
    /**
     * 电话号码
     */
    private String phone;
    
    /**
     * 员工编号
     */
    private String employeId;
    
    /**
     * 渠道编号
     */
    private String channelId;
    
    /**
     * 客户等级
     */
    private Integer level;
    
    /**
     * 微信UnionId
     */
    private String unionId;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 性别
     */
    private Integer sex;
    
    /**
     * 省份
     */
    private String province;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 卡号
     */
    private String cardNo;
    
    /**
     * 生日
     */
    private Date birthday;
    
    /**
     * 地址
     */
    private String address;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmployeId() {
		return employeId;
	}

	public void setEmployeId(String employeId) {
		this.employeId = employeId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
    
}
