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
    
    public void addPersonnel(PersonnelEntity personnel, String[] functionIds);
    
    public PersonnelEntity getPersonnel(String uid);
    
    public void editPersonnel(PersonnelEntity personnel);
}
