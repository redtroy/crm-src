package com.zijincaifu.crm.dao.customer;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;

public interface IInvestItemDao
{
    @Insert
    public void addItem(InvestItemEntity item);
    
    @Update
    public void updateItem(InvestItemEntity item);
    
    public void deleteItems(String customerId);
    
    public List<InvestItemEntity> queryItemList(
            QueryCondition<InvestItemEntity> query);
    
}
