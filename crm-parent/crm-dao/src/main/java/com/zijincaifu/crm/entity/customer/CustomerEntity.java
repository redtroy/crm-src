package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.customer.ICustomerDao;
import com.zijincaifu.crm.enu.customer.CustomerLevelEnum;

/**
 * 客户实体
 * @author dujinxin
 *
 */
@Entity(mapper = ICustomerDao.class)
@Table(name = "CRM_CUSTOMER")
public class CustomerEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1314689595689972219L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 客户编码
     */
    @Column(name = "CUSTOMER_ID")
    @Sn(pattern = "000000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValue = "C")
    private String customerId;
    
    /**
     * 客户姓名
     */
    @Column(name = "NAME")
    private String name;
    
    /**
     * 电话号码
     */
    @Column(name = "PHONE")
    private String phone;
    
    /**
     * 员工编号
     */
    @Column(name = "EMPLOYE_ID")
    private String employeId;
    
    private String employeName;
    
    /**
     * 渠道编号
     */
    @Column(name = "CHANNEL_ID")
    private String channelId;
    
    /**
     * 客户等级
     */
    @Column(name = "LEVEL")
    private CustomerLevelEnum level;
    
    @Column(name = "STATE")
    private Integer state;
    
    /**
     * 微信UnionId
     */
    @Column(name = "UNION_ID")
    private String unionId;
    
    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 性别
     */
    @Column(name = "SEX")
    private Integer sex;
    
    /**
     * 省份
     */
    @Column(name = "PROVINCE")
    private String province;
    
    /**
     * 城市
     */
    @Column(name = "CITY")
    private String city;
    
    /**
     * 省份
     */
    @Column(name = "PROVINCE_NAME")
    private String provinceName;
    
    /**
     * 城市
     */
    @Column(name = "CITY_NAME")
    private String cityName;
    
    /**
     * 卡号
     */
    @Column(name = "CARD_NO")
    private String cardNo;
    
    /**
     * 生日
     */
    @Column(name = "BIRTH_DAY")
    private Date birthday;
    
    /**
     * 地址
     */
    @Column(name = "ADDRESS")
    private String address;
    
    /**
     * 分配员工历史
     */
    @Column(name="EMPLOYE_ID_HISTORY")
    private String employeIdHistory;
    
    private String isNew;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
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
    
    public String getEmployeId()
    {
        return employeId;
    }
    
    public void setEmployeId(String employeId)
    {
        this.employeId = employeId;
    }
    
    public String getChannelId()
    {
        return channelId;
    }
    
    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
    
    public CustomerLevelEnum getLevel()
    {
        return level;
    }
    
    public void setLevel(CustomerLevelEnum level)
    {
        this.level = level;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getCardNo()
    {
        return cardNo;
    }
    
    public void setCardNo(String cardNo)
    {
        this.cardNo = cardNo;
    }
    
    public Date getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(Date birthday)
    {
        this.birthday = birthday;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getProvinceName()
    {
        return provinceName;
    }
    
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }
    
    public String getCityName()
    {
        return cityName;
    }
    
    public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
    
    public Integer getState()
    {
        return state;
    }
    
    public void setState(Integer state)
    {
        this.state = state;
    }
    
    public String getEmployeName()
    {
        return employeName;
    }
    
    public void setEmployeName(String employeName)
    {
        this.employeName = employeName;
    }
    
    public String getIsNew()
    {
        return isNew;
    }
    
    public void setIsNew(String isNew)
    {
        this.isNew = isNew;
    }

    public String getEmployeIdHistory()
    {
        return employeIdHistory;
    }

    public void setEmployeIdHistory(String employeIdHistory)
    {
        this.employeIdHistory = employeIdHistory;
    }
    
}
