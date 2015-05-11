package com.zijincaifu.service.impl.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.dao.customer.ICustomerDao;
import com.zijincaifu.entity.customer.CustomerEntity;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.service.customer.ICustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService
{
    @Autowired
    private ICustomerDao customerDao;
    
    @Override
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException
    {
        try
        {
            QueryCondition<CustomerEntity> condition = new QueryCondition<CustomerEntity>();
            condition.setPage(query);
            List<CustomerEntity> list = customerDao.queryCustomerList(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException();
        }
    }
}
