package com.zijincaifu.crm.enu.customer;

public enum InvestItemStateEnum
{
    REGIST(0, "已报名"), INVEST(1, "已投资"), GIVEUP(2, "已放弃");
    
    // 成员变量
    private Integer id;
    
    private String name;
    
    private InvestItemStateEnum(Integer id, String name)
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
