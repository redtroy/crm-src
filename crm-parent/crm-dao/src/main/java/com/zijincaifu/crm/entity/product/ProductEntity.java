package com.zijincaifu.crm.entity.product;

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
import com.zijincaifu.crm.dao.product.IProductDao;

/**
 * 产品信息
 * 
 * @author Administrator
 *
 */
@Entity(mapper = IProductDao.class)
@Table(name = "CRM_PRODUCT")
public class ProductEntity extends Pagable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7419898311619582588L;
	
	/**
	 * 主键
	 */
	@Id(column="ID")
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;
	
	/**
	 * 产品编号
	 */
	@Column(name="PRODUCTID")
	@Sn(pattern = "00000", step = 1, table = "T_SN", stub = "F_SN_NAME", sn = "F_SN_NUMBER", stubValue = "P")
	private String productId;
	
	/**
	 * 产品名称
	 */
	@Column(name="NAME")
	private String name;
	
	/**
	 * 产品描述
	 */
	@Column(name="REMARK")
	private String remark;
	
	/**
	 * 产品链接
	 */
	@Column(name="PRODUCTURL")
	private String productUrl;
	
	/**
	 * 新增产品时间
	 */
	@Column(name="ADDTIME")
	private Date addTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String describe) {
		this.remark = describe;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	

}
