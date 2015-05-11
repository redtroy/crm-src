package com.zijincaifu.crm.entity.customer;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 推荐明细
 * @author dujinxin
 *
 */
public class RecommendEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2698729228315744581L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 产品编码
     */
    private String productId;
    
    /**
     * 微信UnionId
     */
    private String unionId;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 父编码
     */
    private String parentId;
    
    /**
     * 等级
     */
    private String level;

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

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
    
}
