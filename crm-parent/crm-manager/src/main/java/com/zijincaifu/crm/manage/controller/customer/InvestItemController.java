package com.zijincaifu.crm.manage.controller.customer;

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
import com.zijincaifu.crm.entity.customer.InvestItemEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.enu.customer.InvestItemStateEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.model.customer.InvestItemModel;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.customer.IInvestItemService;

@Controller
@RequestMapping("/customer/invest")
public class InvestItemController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IInvestItemService itemService;
    
    @RequestMapping("/query")
    public String query(String customerId, String channelId, ModelMap map)
            throws WebException
    {
        try
        {
            List<InvestItemModel> list = itemService.queryItems(customerId);
            InvestItemStateEnum[] states = InvestItemStateEnum.values();
            map.put("list", list);
            map.put("channelId", channelId);
            map.put("customerId", customerId);
            map.put("states", states);
            return "manage/customer/investItem";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户投资信息错误", e, this.getClass());
            throw new WebException("查询客户投资信息错误", e);
        }
    }
    
    @RequestMapping("/modify")
    public @ResponseBody Map<String, Object> modify(InvestItemEntity item,
            HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity login = getLoginInfo(session);
            if (login == null)
            {
                //throw new WebException("登陆超时，请重新登陆");
            }
            if (item == null)
            {
                throw new WebException("投资信息不能为空");
            }
            itemService.modify(item);
            map.put("isOK", true);
        }
        catch (Exception e)
        {
            SxjLogger.error("修改客户投资信息错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/add")
    public @ResponseBody Map<String, Object> add(InvestItemEntity item,
            HttpSession session) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity login = getLoginInfo(session);
            if (login == null)
            {
                //throw new WebException("登陆超时，请重新登陆");
            }
            if (item == null)
            {
                throw new WebException("投资信息不能为空");
            }
            itemService.add(item);
            map.put("isOK", true);
            map.put("id", item.getId());
        }
        catch (Exception e)
        {
            SxjLogger.error("添加客户投资信息错误", e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("/getItem")
    public String getItemModel(String id, String itemIndex, ModelMap map)
            throws WebException
    {
        try
        {
            InvestItemModel model = itemService.getItemModel(id);
            InvestItemStateEnum[] states = InvestItemStateEnum.values();
            map.put("model", model);
            map.put("itemIndex", itemIndex);
            map.put("states", states);
            return "manage/customer/invest";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询客户投资信息错误", e, this.getClass());
            throw new WebException("查询客户投资信息错误", e);
        }
    }
}
