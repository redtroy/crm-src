package com.zijincaifu.service.personnel;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;

public interface IPersonnelService
{
    /**
     * @param personnel
     * @throws ServiceException
     */
    
    public List<PersonnelEntity> queryPersonnels(PersonnelQuery query)
            throws ServiceException;
    
    public void addPersonnel(PersonnelEntity personnel, String[] functionIds)
            throws ServiceException;
    
    public PersonnelEntity getPersonnel(String uid) throws ServiceException;
    
    public void editPersonnel(PersonnelEntity personnel, String[] functionIds)
            throws ServiceException;
    
    public void editPersonnel(PersonnelEntity personnel)
            throws ServiceException;
    
    public List<PersonnelEntity> autoPersonnel(PersonnelQuery query)
            throws ServiceException;
}
