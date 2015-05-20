package com.zijincaifu.service.personnel;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.model.personnel.FunctionModel;

public interface IFunctionService
{
    public List<FunctionModel> queryTreeFunctions() throws ServiceException;
}
