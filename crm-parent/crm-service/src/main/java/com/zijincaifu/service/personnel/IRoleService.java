package com.zijincaifu.service.personnel;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.crm.entity.personnel.FunctionRoleEntity;

public interface IRoleService
{
    public void removeRoles(String uid) throws ServiceException;
    
    public List<FunctionEntity> getAllRoleFunction(String uid)
            throws ServiceException;
    
    public List<FunctionRoleEntity> getRoleList(String uid)
            throws ServiceException;
}
