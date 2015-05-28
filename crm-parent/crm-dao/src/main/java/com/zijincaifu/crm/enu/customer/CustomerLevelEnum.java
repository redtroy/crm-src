package com.zijincaifu.crm.enu.customer;

public enum CustomerLevelEnum
{
    NEW(0, "新客户"), READY(1, "准客户"), LARGE(2, "大客户"), VIP(3, "VIP客户");
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private CustomerLevelEnum(Integer id, String name)
    {
        this.name = name;
        this.id = id;
    }
    
    public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
