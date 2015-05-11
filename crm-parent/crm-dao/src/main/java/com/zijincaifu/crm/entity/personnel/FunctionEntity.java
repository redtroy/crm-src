package com.zijincaifu.crm.entity.personnel;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 系统操作定义
 * @author dujinxin
 *
 */
public class FunctionEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8521022983941860951L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 标题
     */
    private String title;
    
    /**
     * 链接
     */
    private String url;
    
    /**
     * 父ID
     */
    private String parentId;
    
    /**
     * 层级
     */
    private Integer level;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
    
}
