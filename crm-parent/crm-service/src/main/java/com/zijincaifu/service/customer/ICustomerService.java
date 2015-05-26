package com.zijincaifu.service.customer;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.model.customer.OpenCustomerModel;

public interface ICustomerService
{
    public List<CustomerEntity> queryCustomer(CustomerQuery query)
            throws ServiceException;
    
    public boolean addCustomer(CustomerEntity customer, String productId)
            throws ServiceException;
    
    public boolean addWeixinCustomer(OpenCustomerModel model)
            throws ServiceException;
    
    public void modifyCustomer(CustomerEntity customer,String uId) throws ServiceException;
    
    public CustomerEntity getCustomer(String customerId);
    
    public void updateCustomer(CustomerEntity customer,String uId);
    
    public void deleteCustomer(String customerId);
}
