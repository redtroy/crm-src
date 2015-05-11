package com.zijincaifu.service.personnel;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.ResultList;
import com.zijincaifu.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;

public interface IPersonnelService
{
    /**
     * @param personnel
     * @throws ServiceException
     */
    
    public ResultList<PersonnelEntity> queryPersonnels(PersonnelQuery query)
            throws ServiceException;
}
