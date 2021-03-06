package com.zijincaifu.crm.dao.personnel;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.crm.entity.personnel.FunctionRoleEntity;

public interface IFunctionRoleDao
{
    /**
     * 添加权限
     *
     * @param roles
     **/
    @BatchInsert
    public void addRoles(List<FunctionRoleEntity> roles);
    
    /**
     * 删除权限
     *
     * @param persionId
     **/
    public void deleteRoles(String persionId);
    
    /**
     * 获取授权操作列表
     * 
     * @return
     * @throws SQLException2
     */
    public List<FunctionEntity> getAllRoleFunction(String persionId);
    
    /**
     * 获取权限列表
     * @param persionId
     * @return
     */
    public List<FunctionRoleEntity> getRoleList(String persionId);
    
}
