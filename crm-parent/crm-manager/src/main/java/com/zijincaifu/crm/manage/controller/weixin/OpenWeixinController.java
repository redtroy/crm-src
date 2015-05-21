package com.zijincaifu.crm.manage.controller.weixin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.spring.modules.web.MediaTypes;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.OpenCustomerModel;

@Controller
@RequestMapping("/open")
public class OpenWeixinController extends BaseController
{
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public void addCustomer(@RequestBody String json,
            HttpServletResponse response)
    {
        try
        {
            OpenCustomerModel cs = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(json, OpenCustomerModel.class);
            System.out.println(cs);
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
