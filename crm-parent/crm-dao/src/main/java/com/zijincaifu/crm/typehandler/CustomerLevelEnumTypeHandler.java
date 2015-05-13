package com.zijincaifu.crm.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.zijincaifu.crm.enu.customer.CustomerLevelEnum;

public class CustomerLevelEnumTypeHandler extends
        EnumOrdinalTypeHandler<CustomerLevelEnum>
{
    
    public CustomerLevelEnumTypeHandler(Class<CustomerLevelEnum> type)
    {
        super(type);
    }
    
}
