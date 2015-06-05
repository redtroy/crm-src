package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.sxj.util.common.StringUtils;
import com.zijincaifu.crm.dao.customer.IRecommendDao;

/**
 * 推荐明细
 * @author dujinxin
 *
 */
@Entity(mapper = IRecommendDao.class)
@Table(name = "CRM_RECOMMEND")
public class RecommendEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2698729228315744581L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 投资明细ID
     */
    @Column(name = "INVEST_ID")
    private String investId;
    
    /**
     * 微信UnionId
     */
    @Column(name = "UNION_ID")
    private String unionId;
    
    /**
     * 姓名
     */
    @Column(name = "NAME")
    private String name;
    
    /**
     * 父编码
     */
    @Column(name = "PARENT_ID")
    private String parentId;
    
    /**
     * CRM编号
     */
    @Column(name = "UID")
    private String uid;
    
    /**
     * 等级
     */
    @Column(name = "LEVEL")
    private Integer level;
    
    private String level_zh;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getInvestId()
    {
        return investId;
    }
    
    public void setInvestId(String investId)
    {
        this.investId = investId;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public Integer getLevel()
    {
        return level;
    } 
    
    public void setLevel(Integer level)
    {
        this.level = level;
    }
    
    public String getLevel_zh()
    {
        
        return StringUtils.transition(this.getLevel() + "");
    }
    
    public String getUid()
    {
        return uid;
    }
    
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    
}
