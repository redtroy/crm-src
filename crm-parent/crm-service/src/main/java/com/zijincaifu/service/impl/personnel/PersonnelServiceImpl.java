package com.zijincaifu.service.impl.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.sxj.util.persistent.ResultList;
import com.sxj.util.persistent.ResultListImpl;
import com.zijincaifu.dao.personnel.IPersonnelDao;
import com.zijincaifu.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IPersonnelService;

@Service
@Transactional
public class PersonnelServiceImpl implements IPersonnelService
{
    @Autowired
    private IPersonnelDao personnelDao;
    
    
    @Override
    public ResultList<PersonnelEntity> queryPersonnels(PersonnelQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<PersonnelEntity> condition = new QueryCondition<PersonnelEntity>();
            ResultList<PersonnelEntity> res = new ResultListImpl<PersonnelEntity>();
            if (query == null)
            {
                return res;
            }
            condition.addCondition("id", query.getId());
            condition.addCondition("uid", query.getUid());
            condition.addCondition("name", query.getName());
            condition.addCondition("company", query.getCompany());
            condition.setPage(query);
            List<PersonnelEntity> list = personnelDao.queryPersonnel(condition);
            query.setPage(condition);
            res.setResults(list);
            return res;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询用戶信息错误", e);
        }
    }
    
}
