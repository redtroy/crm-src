package com.zijincaifu.service.impl.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.dao.personnel.IFunctionRoleDao;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.crm.entity.personnel.FunctionRoleEntity;
import com.zijincaifu.service.personnel.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService
{
    
    @Autowired
    private IFunctionRoleDao roleDao;
    
    @Override
    @Transactional
    public void removeRoles(String accountId) throws ServiceException
    {
        try
        {
            roleDao.deleteRoles(accountId);
            HierarchicalCacheManager.evict(CacheLevel.REDIS,
                    "sys_role_menu",
                    accountId);
        }
        catch (Exception e)
        {
            SxjLogger.error("删除系统权限失败", e, this.getClass());
            throw new ServiceException("删除系统权限失败", e.getMessage());
        }
        
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FunctionEntity> getAllRoleFunction(String uid)
            throws ServiceException
    {
        try
        {
            List<FunctionEntity> list = roleDao.getAllRoleFunction(uid);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error("查询所有权限错误", e, this.getClass());
            throw new ServiceException("查询所有权限错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<FunctionRoleEntity> getRoleList(String uid)
            throws ServiceException
    {
        try
        {
            return roleDao.getRoleList(uid);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询所有权限错误", e, this.getClass());
            throw new ServiceException("查询所有权限错误", e);
        }
    }
    
}
