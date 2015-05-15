package com.zijincaifu.crm.dao.customer;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.model.customer.InvestItemModel;

public interface IInvestItemDao
{
    @Insert
    public void addItem(InvestItemEntity item);
    
    @Update
    public void updateItem(InvestItemEntity item);
    
    public void deleteItems(String customerId);
    
    public List<InvestItemModel> queryItemList(
            QueryCondition<InvestItemEntity> query);
    
}
