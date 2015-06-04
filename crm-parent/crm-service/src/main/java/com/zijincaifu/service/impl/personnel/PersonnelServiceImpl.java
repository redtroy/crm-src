package com.zijincaifu.service.impl.personnel;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.IOrganizationDao;
import com.zijincaifu.crm.dao.personnel.IFunctionRoleDao;
import com.zijincaifu.crm.dao.personnel.IPersonnelDao;
import com.zijincaifu.crm.entity.customer.OrganizationEntity;
import com.zijincaifu.crm.entity.personnel.FunctionRoleEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IPersonnelService;

@Service
public class PersonnelServiceImpl implements IPersonnelService
{
    @Autowired
    private IPersonnelDao personnelDao;
    
    @Autowired
    private IFunctionRoleDao roleDao;
    
    @Autowired
    private IOrganizationDao organizationDao;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<PersonnelEntity> queryPersonnels(PersonnelQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<PersonnelEntity> condition = new QueryCondition<PersonnelEntity>();
            if (query == null)
            {
                return null;
            }
            condition.addCondition("id", query.getId());
            condition.addCondition("uid", query.getUid());
            condition.addCondition("name", query.getName());
            condition.addCondition("company", query.getCompany());
            condition.setPage(query);
            List<PersonnelEntity> list = personnelDao.queryPersonnel(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询用戶信息错误", e);
        }
    }
    
    @Override
    @Transactional
    public void addPersonnel(PersonnelEntity personnel, String[] functionIds)
            throws ServiceException
    {
        try
        {
            personnelDao.addPersonnel(personnel);
            
            if (functionIds != null && functionIds.length > 0)
            {
                List<FunctionRoleEntity> roles = new ArrayList<FunctionRoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        FunctionRoleEntity role = new FunctionRoleEntity();
                        role.setEmployeeId(personnel.getUid());
                        role.setFunctionId(functionIds[i]);
                        roles.add(role);
                    }
                }
                if (!roles.isEmpty())
                {
                    roleDao.addRoles(roles);
                }
                
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增员工信息错误", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public PersonnelEntity getPersonnel(String uid)
    {
        return personnelDao.getPersonnel(uid);
    }
    
    @Override
    @Transactional
    public void editPersonnel(PersonnelEntity personnel, String[] functionIds)
    {
        try
        {
            if (functionIds != null && functionIds.length > 0)
            {
                List<FunctionRoleEntity> roles = new ArrayList<FunctionRoleEntity>();
                for (int i = 0; i < functionIds.length; i++)
                {
                    if (functionIds[i] != null
                            && !"none".equals(functionIds[i]))
                    {
                        FunctionRoleEntity role = new FunctionRoleEntity();
                        role.setEmployeeId(personnel.getUid());
                        role.setFunctionId(functionIds[i]);
                        roles.add(role);
                    }
                }
                if (!CollectionUtils.isEmpty(roles))
                {
                    roleDao.deleteRoles(personnel.getUid());
                    roleDao.addRoles(roles);
                }
            }
            else
            {
                roleDao.deleteRoles(personnel.getUid());
            }
            personnelDao.updatePersonnel(personnel);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改员工信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void editPersonnel(PersonnelEntity personnel)
            throws ServiceException
    {
        try
        {
            personnelDao.updatePersonnel(personnel);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改员工信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public List<PersonnelEntity> autoPersonnel(PersonnelQuery query)
    {
        try
        {
            QueryCondition<PersonnelEntity> condition = new QueryCondition<PersonnelEntity>();
            if (query == null)
            {
                return null;
            }
            condition.addCondition("uid", query.getUid());
            condition.setPage(query);
            List<PersonnelEntity> list = personnelDao.autoPersonnel(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询用戶信息错误", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<OrganizationEntity> queryOrg(String parentId)
    {
        try
        {
            List<OrganizationEntity> list = organizationDao.queryOrg(parentId);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询层级信息错误", e);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public OrganizationEntity getOrg(String orgId)
    {
        try
        {
            return organizationDao.getOrg(orgId);
        }
        catch (Exception e)
        {
            throw new ServiceException("查询层级信息错误", e);
        }
    }
    
}
