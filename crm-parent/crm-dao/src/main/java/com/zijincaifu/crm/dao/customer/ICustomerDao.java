package com.zijincaifu.crm.dao.customer;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.customer.CustomerEntity;

public interface ICustomerDao
{
    @Insert
    public void addCustomer(CustomerEntity customer);
    
    @Update
    public void updateCustomer(CustomerEntity customer);
    
    @Delete
    public void deleteCustomer(String id);
    
    public void getCustomer(String customerId);
    
    public List<CustomerEntity> queryCustomerList(
            QueryCondition<CustomerEntity> query);
    
}
