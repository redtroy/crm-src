package com.zijincaifu.service.impl.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;
import com.zijincaifu.crm.dao.personnel.IPersonnelDao;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IPersonnelService;

@Service
@Transactional
public class PersonnelServiceImpl implements IPersonnelService
{
    @Autowired
    private IPersonnelDao personnelDao;
    
    
    @Override
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
    public void addPersonnel(PersonnelEntity personnel) throws ServiceException
    {
        try
        {
            personnelDao.addPersonnel(personnel);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增用戶信息错误", e);
        }
    }


    @Override
    public PersonnelEntity getPersonnel(String uid)
    {
        return personnelDao.getPersonnel(uid);
    }


    @Override
    public void editPersonnel(PersonnelEntity personnel)
    {
        personnelDao.updatePersonnel(personnel);
    }


    @Override
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
    
}
