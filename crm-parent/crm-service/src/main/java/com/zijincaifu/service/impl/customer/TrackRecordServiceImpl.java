package com.zijincaifu.service.impl.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.ITrackRecordDao;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;
import com.zijincaifu.crm.model.customer.InvestItemModel;
import com.zijincaifu.service.customer.ITrackRecordService;

@Service
public class TrackRecordServiceImpl implements ITrackRecordService
{
    @Autowired
    private ITrackRecordDao trackRecordDao;
    
    @Override
    public List<TrackRecordEntity> query(String customerId)
            throws ServiceException
    {
        try
        {
            QueryCondition<TrackRecordEntity> query = new QueryCondition<>();
            query.addCondition("customerId", customerId);
            List<TrackRecordEntity> list = trackRecordDao.queryTrackRecordList(query);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询跟踪记录列表错误", e);
        }
        
    }

    @Override
    public void addTrackRecord(TrackRecordEntity trackRecord)
    {
        try
        {
            trackRecordDao.addTrackRecord(trackRecord);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增跟踪记录信息错误", e);
        }
    }
    
}
