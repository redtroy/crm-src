package com.zijincaifu.service.rfid;

import java.sql.SQLException;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.rfid.RfidKeyEntity;

public interface IRfidKeyService
{
    
    public Long getKey() throws ServiceException;
    
    public Long getKey(Integer step) throws ServiceException;
    
    public RfidKeyEntity refetchKey(Integer step) throws SQLException;
    
}
