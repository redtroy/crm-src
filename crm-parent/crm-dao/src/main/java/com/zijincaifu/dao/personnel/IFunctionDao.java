package com.zijincaifu.dao.personnel;

import java.util.List;

import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.entity.personnel.FunctionEntity;

public interface IFunctionDao
{
    public List<FunctionEntity> queryFunction(
            QueryCondition<FunctionEntity> query);
    
}
