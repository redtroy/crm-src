package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;

public interface ITrackRecordService
{
    public List<TrackRecordEntity> query(String customerId)
            throws ServiceException;

    public void addTrackRecord(TrackRecordEntity trackRecord);
}
