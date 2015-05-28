package com.zijincaifu.crm.manage.login;

import java.io.Serializable;
import java.util.Set;

public class PublishMessage implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5000233438872829390L;
    
    private String type;
    
    private String userId;
    
    private Set<String> roles;
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public Set<String> getRoles()
    {
        return roles;
    }
    
    public void setRoles(Set<String> roles)
    {
        this.roles = roles;
    }
    
}
