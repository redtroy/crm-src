package com.zijincaifu.model.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.zijincaifu.crm.entity.customer.RecommendEntity;

public class OpenCustomerModel implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 7464027692582171909L;
    
    private String name;
    
    private Integer sex;
    
    private String phone;
    
    private String unionId;
    
    private String productId;
    
    private String channelId;
    
    private String employeeId;
    
    private List<RecommendEntity> recommen;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getUnionId()
    {
        return unionId;
    }
    
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }
    
    public List<RecommendEntity> getRecommen()
    {
        return recommen;
    }
    
    public void setRecommen(List<RecommendEntity> recommen)
    {
        this.recommen = recommen;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getChannelId()
    {
        return channelId;
    }
    
    public void setChannelId(String channelId)
    {
        this.channelId = channelId;
    }
    
    public String getEmployeeId()
    {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
    }
    
    public static void main(String[] args)
    {
        OpenCustomerModel model = new OpenCustomerModel();
        model.setName("张三");
        model.setSex(1);
        model.setPhone("13888888888");
        model.setUnionId("AAAAAAAAAAAAAAAAAAAAAA");
        model.setProductId("产品ID");
        model.setChannelId("cccccc");
        model.setEmployeeId("11111");
        List<RecommendEntity> recommenList = new ArrayList<>();
        RecommendEntity r1 = new RecommendEntity();
        r1.setUnionId("11111111");
        r1.setLevel(1);
        r1.setParentId("000000");
        
        RecommendEntity r2 = new RecommendEntity();
        r2.setUnionId("222222");
        r2.setLevel(2);
        r2.setParentId("1111111");
        
        RecommendEntity r3 = new RecommendEntity();
        r3.setUnionId("333333");
        r3.setLevel(3);
        r3.setParentId("2222222");
        
        recommenList.add(r1);
        recommenList.add(r2);
        recommenList.add(r3);
        
        model.setRecommen(recommenList);
        
        String json = JsonMapper.nonDefaultMapper().toJson(model);
        
        System.out.println(json);
    }
    
}
