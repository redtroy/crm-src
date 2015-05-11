package com.zijincaifu.crm.dao.customer;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;

public interface ITrackRecordDao
{
    
    @Insert
    public void addTrackRecord(TrackRecordEntity record);
    
    public void deleteTrackRecord(String customerId);
    
    public List<TrackRecordEntity> queryTrackRecordList(
            QueryCondition<TrackRecordEntity> query);
    
}
