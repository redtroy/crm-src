package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.model.customer.InvestItemModel;

public interface IInvestItemService
{
    
    public List<InvestItemModel> queryItems(String customerId)
            throws ServiceException;
    
    public void modify(InvestItemEntity item) throws ServiceException;
    
    public void add(InvestItemEntity item) throws ServiceException;
    
    public InvestItemModel getItemModel(String id) throws ServiceException;
    
    public List<InvestItemModel> queryItemsByProductId(String productId);
    
    public List<InvestItemModel> queryItemsByChannelId(String channelId);
    
}
