package com.zijincaifu.service.product;

import java.util.List;

import com.sxj.util.exception.ServiceException;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.model.product.ProductQuery;

public interface IProductService
{

    /**
     * @param product
     * @throws ServiceException
     */
    
    public List<ProductEntity> queryProducts(ProductQuery query);

    public void addProduct(ProductEntity product);

    public ProductEntity getProduct(String productId);

    public void editProduct(ProductEntity product);

    public void deleteProduct(ProductEntity product);
    
}
