package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;
import java.util.Date;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.customer.ITrackRecordDao;

/**
 * 客户跟踪记录
 * @author dujinxin
 *
 */
@Entity(mapper = ITrackRecordDao.class)
@Table(name = "CRM_TRACK_RECORD")
public class TrackRecordEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8207303991405688934L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 客户编号
     */
    @Column(name = "CUSTOMER_ID")
    private String customerId;
    
    /**
     * 创建日期
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    
    /**
     * 描述
     */
     @Column(name = "REMARK")
    private String remark;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    
}
