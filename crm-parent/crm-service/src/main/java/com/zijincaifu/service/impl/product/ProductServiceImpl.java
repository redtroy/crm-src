package com.zijincaifu.service.impl.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.util.exception.ServiceException;
import com.sxj.util.persistent.QueryCondition;
import com.zijincaifu.crm.dao.product.IProductDao;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.model.product.ProductQuery;
import com.zijincaifu.service.product.IProductService;

@Service
@Transactional
public class ProductServiceImpl implements IProductService
{
    @Autowired
    private IProductDao productDao;
    
    @Override
    public List<ProductEntity> queryProducts(ProductQuery query)
    {
        try
        {
            QueryCondition<ProductEntity> condition = new QueryCondition<ProductEntity>();
            if (query == null)
            {
                return null;
            }
            condition.addCondition("productId", query.getProductId());
            condition.addCondition("name", query.getName());
            condition.addCondition("startTime", query.getStartDate());
            condition.addCondition("endTime", query.getEndDate());
            condition.setPage(query);
            List<ProductEntity> list = productDao.queryProduct(condition);
            query.setPage(condition);
            return list;
        }
        catch (Exception e)
        {
            throw new ServiceException("查询产品信息错误", e);
        }
    }
    
}
