package com.zijincaifu.crm.manage.controller.weixin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.OpenCustomerModel;

@Controller
@RequestMapping("/open")
public class OpenWeixinController extends BaseController
{
    @RequestMapping(value = "/addCustomer", method = RequestMethod.GET)
    public void addUser(@RequestBody OpenCustomerModel customer,
            HttpServletResponse response)
    {
        try
        {
            String json = JsonMapper.nonDefaultMapper().toJson(customer);
            System.out.println(json);
            PrintWriter out = response.getWriter();
            out.print("1");
            out.flush();
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }
}
