package com.zijincaifu.crm.manage.controller.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.product.ProductQuery;
import com.zijincaifu.service.product.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController
{
    @Autowired
    private IProductService productService;
    
    @RequestMapping("productList")
    public String getProductList(ProductQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<ProductEntity> list = productService.queryProducts(query);
//            PersonnelCompanyEnum[] company = PersonnelCompanyEnum.values();
            //            List<FunctionEntity> functionList = functionService
            //                    .queryChildrenFunctions("0");
            map.put("list", list);
//            map.put("company", company);
//            map.put("functions", functionList);
            map.put("query", query);
            return "manage/product/productList";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
        
    }
}
