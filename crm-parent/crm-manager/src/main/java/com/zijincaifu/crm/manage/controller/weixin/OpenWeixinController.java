package com.zijincaifu.crm.manage.controller.weixin;

import java.io.IOException;
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
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.customer.OpenCustomerModel;
import com.zijincaifu.service.customer.ICustomerService;
import com.zijincaifu.service.personnel.IPersonnelService;

@Controller
@RequestMapping("/open")
public class OpenWeixinController extends BaseController
{
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IPersonnelService personnelService;
    
    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST, consumes = MediaTypes.JSON)
    @ResponseBody
    public void addCustomer(@RequestBody String json,
            HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            SxjLogger.info(json, this.getClass());
            if (StringUtils.isEmpty(json))
            {
                out.print("0");
                return;
            }
            OpenCustomerModel model = JsonMapper.nonEmptyMapper()
                    .getMapper()
                    .readValue(json, OpenCustomerModel.class);
            customerService.addWeixinCustomer(model);
            out.print("1");
            return;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            out.print("0");
        }
        finally
        {
            out.flush();
            out.close();
        }
        
    }
    
    @RequestMapping(value = "/updateUnion", method = RequestMethod.GET)
    @ResponseBody
    public void updateUnion(String phone,String unionId,
            HttpServletResponse response) throws IOException
    {
        PrintWriter out = response.getWriter();
        try
        {
            PersonnelEntity personnel=personnelService.getPersonnelByPhone(phone);
            personnel.setUnionId(unionId);
            personnelService.editPersonnel(personnel);
            out.print("1"); 
            return;
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            out.print("0");
        }
        finally
        {
            out.flush();
            out.close();
        }
        
    }
}
