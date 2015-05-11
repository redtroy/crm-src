package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;
import java.sql.Date;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 客户跟踪记录
 * @author dujinxin
 *
 */
public class TrackRecordEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8207303991405688934L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 客户编号
     */
    private String customerId;
    
    /**
     * 创建日期
     */
    private Date createTime;
    
    /**
     * 描述
     */
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
