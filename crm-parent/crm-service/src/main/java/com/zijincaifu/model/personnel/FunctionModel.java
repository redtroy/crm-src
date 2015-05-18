package com.zijincaifu.model.personnel;

import java.io.Serializable;
import java.util.List;

import com.zijincaifu.crm.entity.personnel.FunctionEntity;

public class FunctionModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 5577853171472611549L;
    
    private FunctionEntity function;
    
    private List<FunctionEntity> children;
    
    public FunctionEntity getFunction()
    {
        return function;
    }
    
    public void setFunction(FunctionEntity function)
    {
        this.function = function;
    }
    
    public List<FunctionEntity> getChildren()
    {
        return children;
    }
    
    public void setChildren(List<FunctionEntity> children)
    {
        this.children = children;
    }
    
}
