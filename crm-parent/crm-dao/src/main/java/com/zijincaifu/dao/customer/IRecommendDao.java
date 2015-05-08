package com.zijincaifu.dao.customer;

import java.util.List;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.entity.customer.RecommendEntity;
import com.zijincaifu.entity.customer.TrackRecordEntity;

public interface IRecommendDao
{
    
    @BatchInsert
    public void insertRecommend(List<RecommendEntity> recomm);
    
    public List<RecommendEntity> query(QueryCondition<TrackRecordEntity> query);
    
}
