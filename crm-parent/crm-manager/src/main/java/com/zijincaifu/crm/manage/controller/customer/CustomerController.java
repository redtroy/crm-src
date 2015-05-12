package com.zijincaifu.crm.manage.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.exception.WebException;
import com.zijincaifu.crm.entity.customer.CustomerEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.CustomerQuery;
import com.zijincaifu.service.customer.ICustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController
{
    
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping("/query")
    public String query(CustomerQuery query, ModelMap map) throws WebException
    {
        try
        {
            query.setPagable(true);
            List<CustomerEntity> list = customerService.queryCustomer(query);
            map.addAttribute("list", list);
            return "mamang/customer/customerList";
        }
        catch (Exception e)
        {
            throw new WebException();
        }
    }
}
