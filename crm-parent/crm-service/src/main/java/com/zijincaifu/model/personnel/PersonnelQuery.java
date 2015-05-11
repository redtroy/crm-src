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
    
    private Integer company;

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

    public Integer getCompany()
    {
        return company;
    }

    public void setCompany(Integer company)
    {
        this.company = company;
    }
    
}
