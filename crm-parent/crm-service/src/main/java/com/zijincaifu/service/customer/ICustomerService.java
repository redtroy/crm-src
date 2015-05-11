package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.entity.customer.CustomerEntity;
import com.zijincaifu.model.customer.CustomerQuery;

public interface ICustomerService
{
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException;
}
