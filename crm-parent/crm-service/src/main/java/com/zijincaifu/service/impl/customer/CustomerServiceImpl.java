package com.zijincaifu.service.impl.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.logger.SxjLogger;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.customer.ICustomerDao;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.customer.IInvestItemService;

@Service
public class CustomerServiceImpl implements ICustomerService
{
    @Autowired
    private ICustomerDao customerDao;
    
    @Autowired
    private IInvestItemService itemService;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<CustomerEntity> condition = new QueryCondition<CustomerEntity>();
            condition.setPage(query);
            condition.addAllCondition(query);
            List<CustomerEntity> list = customerDao.queryCustomerList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("查询客户列表错误", e);
        }
    }
    
    @Override
    @Transactional
    public void addCustomer(CustomerEntity customer) throws ServiceException
    {
        try
        {
            if (customer != null)
            {
                // 获取出生年月日   
                if (customer.getCardNo() != null)
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                customerDao.addCustomer(customer);
                InvestItemEntity item = new InvestItemEntity();
                //item.setProductId(customer);
                item.setChannelId(customer.getChannelId());
                item.setAmount(0d);
                item.setRegistTime(new Date());
                itemService.add(item);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户信息错误", e);
        }
        
    }
    
    @Override
    @Transactional
    public void modifyCustomer(CustomerEntity customer) throws ServiceException
    {
        try
        {
            if (customer != null)
            {
                // 获取出生年月日   
                if (customer.getCardNo() != null)
                {
                    String birthday = customer.getCardNo().substring(6, 14);
                    Date birthdate = null;
                    birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
                    customer.setBirthday(birthdate);
                }
                customerDao.updateCustomer(customer);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new ServiceException("新增客户信息错误", e);
        }
        
    }
}
