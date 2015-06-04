package com.zijincaifu.crm.manage.controller.product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.model.customer.InvestItemModel;
import com.zijincaifu.model.product.ProductQuery;
import com.zijincaifu.service.customer.IInvestItemService;
import com.zijincaifu.service.product.IProductService;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController
{
    @Autowired
    private IProductService productService;
    
    @Autowired
    private IInvestItemService investItemService;
    
    @RequestMapping("productList")
    public String getProductList(ProductQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
                query.setShowCount(15);
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
    
    @RequestMapping("loadAddProduct")
    public String loadAddProduct(ModelMap map) throws WebException
    {
        try
        {
            return "manage/product/productAdd";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("addProduct")
    public @ResponseBody Map<String, Object> addProduct(ProductEntity product)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            product.setAddTime(DateTimeUtils.getCurrentLocaleTime());
            productService.addProduct(product);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("loadEditProduct")
    public String loadEditProduct(String productId, ModelMap map)
            throws WebException
    {
        try
        {
            ProductEntity product = productService.getProduct(productId);
            map.put("product", product);
            return "manage/product/productEdit";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("editProduct")
    public @ResponseBody Map<String, Object> editProduct(ProductEntity product)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            //            personnel.setFreezeStatus(1);
            //            personnel.setAddTime(DateTimeUtils.getCurrentLocaleTime());
            List<InvestItemModel> invest=investItemService.queryItemsByProductId(product.getProductId());
            if(invest.size()!=0){
                map.put("isOK", false);
                map.put("error", "该产品已进行关联,不能修改");
            }else{
                productService.editProduct(product);
                map.put("isOK", true);
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("deleteProduct")
    public @ResponseBody Map<String, Object> deleteProduct(String productId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ProductEntity product = productService.getProduct(productId);
            List<InvestItemModel> invest=investItemService.queryItemsByProductId(productId);
            if(invest.size()!=0){
                map.put("isOK", "error");
                map.put("error", "该产品已进行关联,不能删除");
            }else{
                productService.deleteProduct(product);
                map.put("isOK", "delete");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", "error");
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    /**
     * 自动感应产品
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoProduct")
    public @ResponseBody Map<String, String> autoComple(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        ProductQuery query = new ProductQuery();
        if (keyword != "" && keyword != null)
        {
            query.setName(keyword);
        }
        List<ProductEntity> list = productService.queryProducts(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (ProductEntity product : list)
        {
            sb = "{\"title\":\"" + product.getName() + "\",\"result\":\""
                    + product.getProductId() + "\",\"url\":\""+product.getProductUrl()+"\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
}
