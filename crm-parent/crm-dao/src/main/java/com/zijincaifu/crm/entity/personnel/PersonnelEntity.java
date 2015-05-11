package com.zijincaifu.crm.entity.personnel;

import java.io.Serializable;
import java.sql.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Sn;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.personnel.IPersonnelDao;
import com.zijincaifu.crm.enu.personnel.PersonnelCompanyEnum;

/**
 * 员工信息
 * @author Administrator
 *
 */
@Entity(mapper = IPersonnelDao.class)
@Table(name = "CRM_PERSONNEL")
public class PersonnelEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -6604367198110501481L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 员工编号
     */
    @Column(name = "UID")
    @Sn(pattern = "00000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValue = "E")
    private String uid;
    
    /**
     * 员工信息
     */
    @Column(name = "NAME")
    private String name;
    
    /**
     * 电话号码
     */
    @Column(name = "PHONE")
    private String phone;
    
    /**
     * 所属分公司
     */
    @Column(name = "COMPANY")
    private PersonnelCompanyEnum company;
    
    /**
     * 关注微信后生成的UnionId
     */
    @Column(name = "UNIONID")
    private String unionId;
    
    /**
     * 创建时间
     */
    @Column(name = "ADDTIME")
    private Date addTime;
    
    /**
     * 密码
     */
    @Column(name = "PASSWORD")
    private String password;
    
    /**
     * 是否冻结状态(0:冻结,1:未冻结)
     */
    @Column(name = "FREEZESTATUS")
    private Integer freezeStatus;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public void setUid(String uid)
    {
        this.uid = uid;
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
    
    public PersonnelCompanyEnum getCompany()
    {
        return company;
    }
    
    public void setCompany(PersonnelCompanyEnum company)
    {
        this.company = company;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public Date getAddTime()
    {
        return addTime;
    }
    
    public void setAddTime(Date addTime)
    {
        this.addTime = addTime;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public Integer getFreezeStatus()
    {
        return freezeStatus;
    }
    
    public void setFreezeStatus(Integer freezeStatus)
    {
        this.freezeStatus = freezeStatus;
    }
    
}