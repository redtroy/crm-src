package com.zijincaifu.service.impl.customer;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.IInvestItemDao;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;
import com.zijincaifu.crm.model.customer.InvestItemModel;
import com.zijincaifu.service.customer.IInvestItemService;

@Service
public class InvestItemServiceImpl implements IInvestItemService
{
    
    @Autowired
    private IInvestItemDao itemDao;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<InvestItemModel> queryItems(String customerId)
            throws ServiceException
    {
        try
        {
            QueryCondition<InvestItemEntity> query = new QueryCondition<>();
            query.addCondition("customerId", customerId);
            List<InvestItemModel> list = itemDao.queryItemList(query);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询客户投资列表错误", e);
        }
    }
    
    @Override
    @Transactional
    public void modify(InvestItemEntity item) throws ServiceException
    {
        try
        {
            if (item.getState().equals(InvestItemStateEnum.REGIST))
            {
                item.setRegistTime(new Date());
            }
            if (item.getState().equals(InvestItemStateEnum.INVEST))
            {
                item.setInvestTime(new Date());
            }
            itemDao.updateItem(item);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("修改客户投资列表错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void add(InvestItemEntity item) throws ServiceException
    {
        try
        {
            if (item.getState().equals(InvestItemStateEnum.REGIST))
            {
                item.setRegistTime(new Date());
            }
            if (item.getState().equals(InvestItemStateEnum.INVEST))
            {
                item.setRegistTime(new Date());
                item.setInvestTime(new Date());
            }
            itemDao.addItem(item);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户投资列表错误", e);
        }
        
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public InvestItemModel getItemModel(String id) throws ServiceException
    {
        try
        {
            QueryCondition<InvestItemEntity> query = new QueryCondition<>();
            query.addCondition("id", id);
            List<InvestItemModel> list = itemDao.queryItemList(query);
            if (list != null && list.size() > 0)
            {
                return list.get(0);
            }
            else
            {
                return null;
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("获取客户投资信息错误", e);
        }
    }
    
}
