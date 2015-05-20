package com.zijincaifu.crm.entity.personnel;

import java.io.Serializable;

import com.sxj.mybatis.orm.annotations.Column;
import com.sxj.mybatis.orm.annotations.Entity;
import com.sxj.mybatis.orm.annotations.GeneratedValue;
import com.sxj.mybatis.orm.annotations.GenerationType;
import com.sxj.mybatis.orm.annotations.Id;
import com.sxj.mybatis.orm.annotations.Table;
import com.sxj.mybatis.pagination.Pagable;
import com.zijincaifu.crm.dao.personnel.IFunctionRoleDao;

/**
 * 系统权限
 * @author dujinxin
 *
 */
@Entity(mapper = IFunctionRoleDao.class)
@Table(name = "CRM_FUNCTION_ROLE")
public class FunctionRoleEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6473090420864534616L;
    
    /**
     * 主键
     */
    @Id(column = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    /**
     * 方法ID
     */
    @Column(name = "FUNCTION_ID")
    private String functionId;
    
    /**
     * 员工编号
     */
    @Column(name = "EMPLOYEE_ID")
    private String employeeId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getFunctionId()
    {
        return functionId;
    }
    
    public void setFunctionId(String functionId)
    {
        this.functionId = functionId;
    }
    
    public String getEmployeeId()
    {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }
    
}
