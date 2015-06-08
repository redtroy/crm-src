package com.zijincaifu.crm.manage.controller.weixin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.spring.modules.mapper.JsonMapper;
import com.sxj.spring.modules.web.MediaTypes;
import com.sxj.util.common.ISxjHttpClient;
import com.sxj.util.common.StringUtils;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.channel.CreateCodeEntity;
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
    
    @Autowired
    private ISxjHttpClient httpClient;
    
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
    
    @RequestMapping(value = "/checkProductUrl", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> checkProductUrl(String url){
        Map<String, Object> map = new HashMap<String, Object>();
       try
    {
        
        String res = httpClient.post(url, null);
        if(StringUtil.isBlank(res)||!res.equals("1")){
            map.put("isOK", false);
        }else{
            map.put("isOK", true);
        }
        System.err.println(res);
    }
    catch (ClientProtocolException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
        map.put("isOK", false);
        map.put("error", e.getMessage());
    }
    catch (IOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
        map.put("isOK", false);
        map.put("error", e.getMessage());
    }
        return map;
        
    }
    
    @RequestMapping(value = "/createCode", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> createCode(CreateCodeEntity createCode){
        Map<String, Object> map = new HashMap<String, Object>();
       try
    {
        Map<String,String> params=new HashMap<String,String>();
        params.put("productid", createCode.getProductid());
        params.put("channelid", createCode.getChannelid());
        params.put("employeeid", createCode.getEmployeeid());
        params.put("url", createCode.getUrl());        
        String res = httpClient.post("http://www.mimeng360.com/index.php?g=User&m=Crmweixin&a=findkey", params);
        if(StringUtil.isBlank(res)){
            map.put("isOK", false);
        }else{
            map.put("urls", res);
            map.put("isOK", true);
        }
        System.err.println(res);
    }
    catch (ClientProtocolException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
        map.put("isOK", false);
        map.put("error", e.getMessage());
    }
    catch (IOException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
        map.put("isOK", false);
        map.put("error", e.getMessage());
    }
        return map;
        
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
