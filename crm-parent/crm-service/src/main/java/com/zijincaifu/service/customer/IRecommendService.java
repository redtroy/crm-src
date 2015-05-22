package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.customer.RecommendEntity;

public interface IRecommendService
{
    public void addRecommend(List<RecommendEntity> recommendList)
            throws ServiceException;
    
    public List<RecommendEntity> query(String productId,String channelId)
            throws ServiceException;
}
