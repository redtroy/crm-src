package com.zijincaifu.service.impl.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.IRecommendDao;
import com.zijincaifu.crm.entity.customer.RecommendEntity;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;
import com.zijincaifu.service.customer.IRecommendService;

@Service
public class RecommendServiceImpl implements IRecommendService
{
    
    @Autowired
    private IRecommendDao dao;
    
    @Override
    @Transactional
    public void addRecommend(List<RecommendEntity> recommendList)
            throws ServiceException
    {
        try
        {
            dao.insertRecommend(recommendList);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增推荐明细错误", e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<RecommendEntity> query(String investId) throws ServiceException
    {
        try
        {
            QueryCondition<TrackRecordEntity> query = new QueryCondition<>();
            query.addCondition("investId", investId);
            return dao.query(query);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询推荐明细错误", e);
        }
    }
    
}
