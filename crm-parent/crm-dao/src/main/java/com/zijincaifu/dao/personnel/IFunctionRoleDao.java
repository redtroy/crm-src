package com.zijincaifu.dao.personnel;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.entity.personnel.FunctionEntity;
import com.zijincaifu.entity.personnel.FunctionRoleEntity;

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
    public List<FunctionEntity> getRoleFunction(
            QueryCondition<FunctionRoleEntity> query);
    
    /**
     * 获取授权操作列表
     * 
     * @return
     * @throws SQLException2
     */
    public List<FunctionEntity> getAllRoleFunction(String persionId);
    
}
