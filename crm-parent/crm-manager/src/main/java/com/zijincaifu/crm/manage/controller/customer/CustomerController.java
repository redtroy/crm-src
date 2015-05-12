package com.zijincaifu.crm.manage.controller.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.entity.system.AreaEntity;
import com.zijincaifu.crm.enu.customer.CustomerLevelEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.system.IAreaService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAreaService areaService;
    
    @RequestMapping("/query")
    public String query(CustomerQuery query, ModelMap map) throws WebException
    {
        try
        {
            query.setPagable(true);
            List<CustomerEntity> list = customerService.queryCustomer(query);
            CustomerLevelEnum[] levels = CustomerLevelEnum.values();
            map.put("list", list);
            map.put("levels", levels);
            map.put("query", query);
            return "manage/customer/customerList";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户信息错误", e, this.getClass());
            throw new WebException("查询客户信息错误", e);
        }
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
    
    @RequestMapping("/add")
    public @ResponseBody Map<String, Object> add(CustomerEntity customer,
            HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity login = getLoginInfo(session);
            if (login == null)
            {
                throw new WebException("登陆超时，请重新登陆");
            }
            if (customer == null)
            {
                throw new WebException("客户信息不能为空");
            }
            customer.setEmployeId(login.getUid());
            customer.setChannelId("AAA001");
            customer.setCreateTime(new Date());
            customerService.addCustomer(customer);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("新增客户信息错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
}
