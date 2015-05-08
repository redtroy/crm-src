package com.zijincaifu.entity.customer;

import java.io.Serializable;

import com.sxj.mybatis.pagination.Pagable;

/**
 * 客户实体
 * @author dujinxin
 *
 */
public class CustomerEntity extends Pagable implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1314689595689972219L;
    
    private String id;
    
    private String customerId;
    
    private String name;
    
}
