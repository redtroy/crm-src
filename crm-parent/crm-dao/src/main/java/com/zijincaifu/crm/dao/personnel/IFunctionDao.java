package com.zijincaifu.crm.dao.personnel;

import java.util.List;

import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;

public interface IFunctionDao
{
    public List<FunctionEntity> queryFunction(
            QueryCondition<FunctionEntity> query);
    
}
