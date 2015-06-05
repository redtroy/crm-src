package com.zijincaifu.model.personnel;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

public class PersonnelQuery extends Pagable implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2642334363019225976L;
    
    private String id;
    
    private String uid;
    
    private String name;
    
    private String company;
    
    private String companyStr;

    public String getCompanyStr()
    {
        return companyStr;
    }

    public void setCompanyStr(String companyStr)
    {
        this.companyStr = companyStr;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCompany()
    {
        return company;
    }

    public void setCompany(String company)
    {
        this.company = company;
    }
    
}
