package com.zijincaifu.crm.entity.personnel;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 系统权限
 * @author dujinxin
 *
 */
public class FunctionRoleEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6473090420864534616L;
    
    /**
     * 主键
     */
    private String id;
    
    /**
     * 方法ID
     */
    private String functionId;
    
    /**
     * 员工编号
     */
    private String employeeId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
    
    
}
