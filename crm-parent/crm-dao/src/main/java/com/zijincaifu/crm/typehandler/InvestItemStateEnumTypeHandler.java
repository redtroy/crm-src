package com.zijincaifu.crm.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;

public class InvestItemStateEnumTypeHandler extends
        EnumOrdinalTypeHandler<InvestItemStateEnum>
{
    
    public InvestItemStateEnumTypeHandler(Class<InvestItemStateEnum> type)
    {
        super(type);
    }
    
}
