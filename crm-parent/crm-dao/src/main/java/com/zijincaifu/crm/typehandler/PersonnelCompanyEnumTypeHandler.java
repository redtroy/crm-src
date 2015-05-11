package com.zijincaifu.crm.typehandler;

import org.apache.ibatis.type.EnumOrdinalTypeHandler;

import com.zijincaifu.crm.enu.personnel.PersonnelCompanyEnum;

public class PersonnelCompanyEnumTypeHandler extends
        EnumOrdinalTypeHandler<PersonnelCompanyEnum>
{
    
    public PersonnelCompanyEnumTypeHandler(Class<PersonnelCompanyEnum> type)
    {
        super(type);
    }
    
}
