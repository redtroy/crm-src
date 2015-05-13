package com.zijincaifu.service.system;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.system.AreaEntity;

public interface IAreaService
{
    
    public List<AreaEntity> getChildrenAreas(String parentId)
            throws ServiceException;
    
}
