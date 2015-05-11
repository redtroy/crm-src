package com.zijincaifu.crm.dao.product;

import java.util.List;

import com.sxj.mybatis.orm.annotations.Delete;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.entity.product.ProductEntity;

public interface IProductDao
{
    
    /**
     * 新增产品
     * @param product
     */
    @Insert
    public void addProduct(ProductEntity product);
    
    /**
     * 修改产品
     * @param product
     */
    @Update
    public void updateProduct(ProductEntity product);
    
    /**
     * 删除产品
     * @param id
     */
    @Delete
    public void deleteProduct(String id);
    
    /**
     * 获取产品信息
     * @param id
     */
    public ProductEntity getProduct(String productId);
    
    /**
     * 查看产品
     * @param query
     */
    public List<ProductEntity> queryProduct(QueryCondition<ProductEntity> query);
}
