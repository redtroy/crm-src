package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.model.customer.CustomerQuery;

public interface ICustomerService
{
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException;
    
    public boolean addCustomer(CustomerEntity customer, String productId)
            throws ServiceException;
    
    public void modifyCustomer(CustomerEntity customer) throws ServiceException;

    public CustomerEntity getCustomer(String customerId);

    public void updateCustomer(CustomerEntity customer);

    public void deleteCustomer(String customerId);
}
