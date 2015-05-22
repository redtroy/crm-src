package com.zijincaifu.crm.manage.controller.weixin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.spring.modules.web.MediaTypes;
import com.sxj.util.common.StringUtils;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.OpenCustomerModel;
import com.zijincaifu.service.customer.ICustomerService;

@Controller
@RequestMapping("/open")
public class OpenWeixinController extends BaseController
{
    @Autowired
    private ICustomerService customerService;
    
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public void addCustomer(@RequestBody String json,
            HttpServletResponse response)
    {
        try
        {
            PrintWriter out = response.getWriter();
            if (StringUtils.isEmpty(json))
            {
                out.print("1");
                return;
            }
            
            OpenCustomerModel model = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(json, OpenCustomerModel.class);
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            //out.flush();
            //out.close();
        }
        
    }
}
