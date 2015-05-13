package com.zijincaifu.service.impl.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.system.IAreaDao;
import com.zijincaifu.crm.entity.system.AreaEntity;
import com.zijincaifu.service.system.IAreaService;

@Service
public class AreaServiceImpl implements IAreaService
{
    
    @Autowired
    private IAreaDao areaDao;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<AreaEntity> getChildrenAreas(String parentId)
            throws ServiceException
    {
        try
        {
            QueryCondition<AreaEntity> query = new QueryCondition<AreaEntity>();
            query.addCondition("parentId", parentId);
            List<AreaEntity> list = areaDao.getAreas(query);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询地理信息错误", e);
        }
    }
}
