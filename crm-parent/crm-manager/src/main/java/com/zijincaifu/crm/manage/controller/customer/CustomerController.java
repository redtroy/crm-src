package com.zijincaifu.crm.manage.controller.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.cache.manager.CacheLevel;
import com.sxj.cache.manager.HierarchicalCacheManager;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.entity.system.AreaEntity;
import com.zijincaifu.crm.enu.customer.CustomerLevelEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.model.customer.InvestItemModel;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.customer.IInvestItemService;
import com.zijincaifu.service.customer.ITrackRecordService;
import com.zijincaifu.service.personnel.IPersonnelService;
import com.zijincaifu.service.system.IAreaService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAreaService areaService;
    
    @Autowired
    private IPersonnelService personnelService;
    
    @Autowired
    private IInvestItemService investItemService;
    
    @Autowired
    private ITrackRecordService trackRecordService;
    
    @RequestMapping("/query")
    public String query(CustomerQuery query, ModelMap map) throws WebException
    {
        try
        {
            query.setPagable(true);
            query.setShowCount(15);
            boolean isAdmin = true;
            if (SecurityUtils.getSubject() != null
                    && !SecurityUtils.getSubject().hasRole("4"))
            {
                PersonnelEntity user = getLoginInfo();
                query.setEmployeId(user.getUid());
                isAdmin = false;
            }
            query.setCompanyStr(getLoginInfo().getCompanyStr());
            List<AreaEntity> provinceList = areaService.getChildrenAreas("0");
            List<AreaEntity> cityList = areaService.getChildrenAreas("32");
            map.put("provinceList", provinceList);
            map.put("cityList", cityList);
            List<CustomerEntity> list = customerService.queryCustomer(query);
            for (Iterator<CustomerEntity> iterator = list.iterator(); iterator.hasNext();)
            {
                CustomerEntity customerEntity = iterator.next();
                if (isAdmin)
                {
                    Object isNew = HierarchicalCacheManager.get(CacheLevel.REDIS,
                            "CRM_CUSTOMER_LEVEL_AD",
                            customerEntity.getCustomerId());
                    if (isNew != null)
                    {
                        customerEntity.setIsNew((String) isNew);
                    }
                }
                else
                {
                    Object isNew = HierarchicalCacheManager.get(CacheLevel.REDIS,
                            "CRM_CUSTOMER_LEVEL",
                            customerEntity.getCustomerId());
                    if (isNew != null)
                    {
                        customerEntity.setIsNew((String) isNew);
                    }
                }
                
            }
            CustomerLevelEnum[] levels = CustomerLevelEnum.values();
            map.put("list", list);
            map.put("levels", levels);
            map.put("query", query);
            map.put("loginUid", getLoginInfo().getUid());
            return "manage/customer/customerList";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户信息错误", e, this.getClass());
            throw new WebException("查询客户信息错误", e);
        }
    }
    
    @RequestMapping("/changeNew")
    public @ResponseBody Map<String, Object> changeNew(String customerId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            if (SecurityUtils.getSubject() != null
                    && !SecurityUtils.getSubject().hasRole("4"))
            {
                HierarchicalCacheManager.set(CacheLevel.REDIS,
                        "CRM_CUSTOMER_LEVEL",
                        customerId,
                        "0");
                map.put("isNew", "0");
            }
            else
            {
                HierarchicalCacheManager.set(CacheLevel.REDIS,
                        "CRM_CUSTOMER_LEVEL_AD",
                        customerId,
                        "0");
                map.put("isNew", "0");
            }
            
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户信息错误", e, this.getClass());
            map.put("isNew", "1");
        }
        return map;
    }
    
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap map) throws WebException
    {
        try
        {
            List<AreaEntity> provinceList = areaService.getChildrenAreas("0");
            map.put("provinceList", provinceList);
            return "manage/customer/addCustomer";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户信息错误", e, this.getClass());
            throw new WebException("查询客户信息错误", e);
        }
    }
    
    @RequestMapping("/queryArea")
    public @ResponseBody Map<String, Object> queryArea(String parentId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            List<AreaEntity> areaList = areaService.getChildrenAreas(parentId);
            map.put("areaList", areaList);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户信息错误", e, this.getClass());
            map.put("isOK", false);
        }
        return map;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/add")
    public @ResponseBody Map<String, Object> add(
            @RequestParam(value = "productId") String productId,
            CustomerEntity customer, HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity login = getLoginInfo();
            if (login == null)
            {
                throw new WebException("登陆超时，请重新登陆");
            }
            if (customer == null)
            {
                throw new WebException("客户信息不能为空");
            }
            if (StringUtils.isEmpty(productId))
            {
                throw new WebException("产品非法，请重新选择");
            }
            //TODO 设置员工
            customer.setEmployeId(login.getUid());
            customer.setChannelId("AAA001");
            customer.setCreateTime(new Date());
            customer.setLevel(CustomerLevelEnum.NEW);
            boolean isAdd = customerService.addCustomer(customer, productId);
            HierarchicalCacheManager.set(CacheLevel.REDIS,
                    "CRM_CUSTOMER_LEVEL_AD",
                    customer.getCustomerId(),
                    "1");
            map.put("isOK", true);
            map.put("isAdd", isAdd);
        }
        catch (Exception e)
        {
            SxjLogger.error("新增客户信息错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/modify")
    public @ResponseBody Map<String, Object> modify(CustomerEntity customer,
            HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity login = getLoginInfo();
            if (login == null)
            {
                throw new WebException("登陆超时，请重新登陆");
            }
            if (customer == null)
            {
                throw new WebException("客户信息不能为空");
            }
            customerService.modifyCustomer(customer, getLoginInfo().getUid());
            map.put("isOK", true);
            map.put("customer", customer);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改客户信息错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("changeLevel")
    public @ResponseBody Map<String, Object> changeLevel(String customerId,
            @RequestParam(value = "level") CustomerLevelEnum level)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            CustomerEntity customer = customerService.getCustomer(customerId);
            customer.setLevel(level);
            customerService.updateCustomer(customer, "");
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
    
    /**
     * 自动感应产品
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoPersonnel")
    public @ResponseBody Map<String, String> autoPersonnel(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        PersonnelQuery query = new PersonnelQuery();
        if (keyword != "" && keyword != null)
        {
            query.setUid(keyword);
        }
        List<PersonnelEntity> list = personnelService.autoPersonnel(query);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (PersonnelEntity personnel : list)
        {
            sb = "{\"title\":\"" + personnel.getUid() + " -- "
                    + personnel.getName() + "\",\"result\":\""
                    + personnel.getUid() + "\"}";
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
    
    @RequestMapping("changePersonnel")
    public @ResponseBody Map<String, Object> changePersonnel(String customerId,
            String personnelId) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelQuery query = new PersonnelQuery();
            if (personnelId != "" && personnelId != null)
            {
                query.setUid(personnelId);
            }
            List<PersonnelEntity> list = personnelService.queryPersonnels(query);
            if (list.size() == 0)
            {
                map.put("result", "0");
            }
            else
            {
                CustomerEntity customer = customerService.getCustomer(customerId);
                if (customer.getEmployeIdHistory() == null)
                {
                    customer.setEmployeIdHistory(customer.getEmployeId());
                }
                else
                {
                    customer.setEmployeIdHistory(customer.getEmployeIdHistory()
                            + "," + customer.getEmployeId());
                }
                customer.setEmployeId(personnelId);
                customerService.updateCustomer(customer, "");
                map.put("result", "1");
                HierarchicalCacheManager.set(CacheLevel.REDIS,
                        "CRM_CUSTOMER_LEVEL",
                        customer.getCustomerId(),
                        "1");
            }
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("result", "error");
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("deleteCustomer")
    public @ResponseBody Map<String, Object> deleteCustomer(String customerId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            CustomerEntity customer = customerService.getCustomer(customerId);
            List<InvestItemModel> invests = investItemService.queryItems(customerId);
            List<TrackRecordEntity> tracks = trackRecordService.query(customerId);
            if (customer.getLevel().getId() != 0)
            {
                map.put("isOK", false);
                map.put("error", "该用户不是新客户,不可删除");
            }
            else if (invests.size() > 1)
            {
                map.put("isOK", false);
                map.put("error", "该用户下存在不止一条投资记录,不可删除");
            }
            else if (tracks.size() != 0)
            {
                map.put("isOK", false);
                map.put("error", "该用户下存在跟踪记录,不可删除");
            }
            else
            {
                customerService.deleteCustomer(customerId);
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
    
    @RequestMapping("info")
    public String getCustomerInfo(String customerId, ModelMap map)
            throws WebException
    {
        CustomerEntity customer = customerService.getCustomer(customerId);
        List<AreaEntity> provinceList = areaService.getChildrenAreas("0");
        List<AreaEntity> cityList = areaService.getChildrenAreas("32");
        map.put("provinceList", provinceList);
        map.put("cityList", cityList);
        map.put("model", customer);
        return "manage/customer/customerInfo";
    }
    
}
