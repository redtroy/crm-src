package com.zijincaifu.crm.manage.controller.personnel;

import java.util.Date;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.util.Constraints;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.customer.OrganizationEntity;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.entity.product.ProductEntity;
import com.zijincaifu.crm.enu.personnel.PersonnelCompanyEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.manage.login.PublishMessage;
import com.zijincaifu.model.personnel.FunctionModel;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.model.product.ProductQuery;
import com.zijincaifu.service.personnel.IFunctionService;
import com.zijincaifu.service.personnel.IPersonnelService;
import com.zijincaifu.service.personnel.IRoleService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController
{
    @Autowired
    private IPersonnelService personneService;
    
    @Autowired
    private IFunctionService functionService;
    
    @Autowired
    private IRoleService roleService;
    
    @Autowired
    private RedisTopics topics;
    
    @RequestMapping("personnelList")
    public String getPersonnelList(PersonnelQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            if (SecurityUtils.getSubject() != null
                    && !SecurityUtils.getSubject().hasRole("9"))
            {
                PersonnelEntity user = getLoginInfo();
                query.setUid(user.getUid());
            }
            if(StringUtils.isNotEmpty(query.getCompany())){
                query.setCompanyStr(getLoginInfo().getCompanyStr()+","+query.getCompany());
            }else{
                query.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            query.setShowCount(15);
            List<PersonnelEntity> list = personneService.queryPersonnels(query);
            List<OrganizationEntity> orgStrList=new ArrayList<OrganizationEntity>();
            String[] companyStrs=getLoginInfo().getCompanyStr().split(",");
            for(int i=0;i<companyStrs.length;i++){
                orgStrList.add(personneService.getOrg(companyStrs[i]));
            }
            List<OrganizationEntity> orgList=personneService.queryOrg(getLoginInfo().getCompany()+"");
            map.put("org", orgList);
            map.put("list", list);
            map.put("orgStrList", orgStrList);
            map.put("query", query);
            map.put("loginUid", getLoginInfo().getUid());
            return "manage/personnel/personnelList";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
        
    }
    
    @RequestMapping("loadAddPersonnel")
    public String loadAddPersonnel(ModelMap map) throws WebException
    {
        try
        {
            List<OrganizationEntity> orgStrList=new ArrayList<OrganizationEntity>();
            String[] companyStrs=getLoginInfo().getCompanyStr().split(",");
            for(int i=0;i<companyStrs.length;i++){
                orgStrList.add(personneService.getOrg(companyStrs[i]));
            }
            List<OrganizationEntity> orgList=personneService.queryOrg(getLoginInfo().getCompany()+"");
            map.put("orgStrList", orgStrList);
            map.put("org", orgList);
            List<FunctionModel> allFunction = functionService.queryTreeFunctions();
            map.put("allFunction", allFunction);
            return "manage/personnel/personnelAdd";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("addPersonnel")
    public @ResponseBody Map<String, Object> addPersonnel(
            PersonnelEntity personnel,
            @RequestParam("functionIds") String functionIds)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            String[] ids = null;
            if (StringUtils.isNotEmpty(functionIds))
            {
                ids = functionIds.split(",");
            }
//            String[] companys=companyStr.split(",");
            if(StringUtils.isNotEmpty(personnel.getCompanyStr()+"")){
                personnel.setCompanyStr(getLoginInfo().getCompanyStr()+","+personnel.getCompanyStr());
            }else{
                personnel.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            String[] temCom=personnel.getCompanyStr().split(",");
            OrganizationEntity org=personneService.getOrg(temCom[temCom.length-1]);
//            personnel.setCompanyStr(companyStr);
            personnel.setCompany(Integer.parseInt(temCom[temCom.length-1]));
            personnel.setCompanyName(org.getName());
            personnel.setFreezeStatus(1);
            personnel.setAddTime(new Date());
            personneService.addPersonnel(personnel, ids);
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
    
    @RequestMapping("loadEditPersonnel")
    public String loadEditPersonnel(String uid, ModelMap map)
            throws WebException
    {
        try
        {
            PersonnelEntity personnel = personneService.getPersonnel(uid);
            map.put("personnel", personnel);
            List<OrganizationEntity> orgStrList=new ArrayList<OrganizationEntity>();
            String[] companyStrs=getLoginInfo().getCompanyStr().split(",");
            for(int i=0;i<companyStrs.length;i++){
                orgStrList.add(personneService.getOrg(companyStrs[i]));
            }
            List<OrganizationEntity> orgList=personneService.queryOrg(getLoginInfo().getCompany()+"");
            map.put("orgStrList", orgStrList);
            map.put("parentStr", getLoginInfo().getCompanyStr());
            if(personnel.getCompanyStr().equals(getLoginInfo().getCompanyStr())){
                map.put("ownStr", "false");
            }else{
                map.put("ownStr", personnel.getCompanyStr().split(getLoginInfo().getCompanyStr()+",")[1]);
            }
            map.put("org", orgList);
            List<FunctionEntity> roleList = roleService.getAllRoleFunction(uid);
            List<FunctionModel> allFunction = functionService.queryTreeFunctions();
            map.put("allFunction", allFunction);
            map.put("roleList", roleList);
            return "manage/personnel/personnelEdit";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("editPersonnel")
    public @ResponseBody Map<String, Object> editPersonnel(
            PersonnelEntity personnel,
            @RequestParam("functionIds") String functionIds)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            String[] ids = null;
            Set<String> roles = new HashSet<>();
            if (StringUtils.isNotEmpty(functionIds))
            {
                ids = functionIds.split(",");
                if (!"none".equals(functionIds))
                {
                    for (int i = 0; i < ids.length; i++)
                    {
                        roles.add(ids[i]);
                    }
                }
            }
            if(StringUtils.isNotEmpty(personnel.getCompanyStr()+"")){
                personnel.setCompanyStr(getLoginInfo().getCompanyStr()+","+personnel.getCompanyStr());
            }else{
                personnel.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            String[] temCom=personnel.getCompanyStr().split(",");
            OrganizationEntity org=personneService.getOrg(temCom[temCom.length-1]);
            
            personnel.setCompany(Integer.parseInt(org.getId()));
            personnel.setCompanyName(org.getName());
            personneService.editPersonnel(personnel, ids);
            PublishMessage message = new PublishMessage();
            message.setType("update");
            message.setUserId(personnel.getUid());
            message.setRoles(roles);
            topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(message);
            
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
    
    @RequestMapping("freezePersonnel")
    public @ResponseBody Map<String, Object> freezePersonnel(String uid,
            Integer freezeStatus) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity personnel = personneService.getPersonnel(uid);
            personnel.setFreezeStatus(freezeStatus);
            personneService.editPersonnel(personnel);
            map.put("isOK", true);
            
            PublishMessage message = new PublishMessage();
            message.setType("del");
            message.setUserId(personnel.getUid());
            topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(message);
            
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("initPassword")
    public @ResponseBody Map<String, Object> initPassword(String uid)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity personnel = personneService.getPersonnel(uid);
            int rondom = NumberUtils.getRandomIntInMax(999999);
            String password = StringUtils.getLengthStr(rondom + "", 6, '0');
            personnel.setPassword(password);
            personneService.editPersonnel(personnel);
            map.put("isOK", true);
            map.put("password", password);
            
            PublishMessage message = new PublishMessage();
            message.setType("del");
            message.setUserId(personnel.getUid());
            topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(message);
            
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
    
    @RequestMapping("editPassword")
    public @ResponseBody Map<String, Object> editPassword(String newpassword)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            PersonnelEntity personnel = getLoginInfo();
            personnel.setPassword(newpassword);
            personneService.editPersonnel(personnel);
            map.put("isOK", true);
            
            PublishMessage message = new PublishMessage();
            message.setType("del");
            message.setUserId(personnel.getUid());
            topics.getTopic(Constraints.MANAGER_CHANNEL_NAME).publish(message);
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
     * 获取层级
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("queryOrg")
    public @ResponseBody Map<String, Object> queryOrg(
            HttpServletRequest request, HttpServletResponse response,
            String parentId) throws IOException
    {
        Map<String,Object> map=new HashMap<>();
        List<OrganizationEntity> list = personneService.queryOrg(parentId);
        map.put("list", list);
//        List<String> strlist = new ArrayList<String>();
//        String sb = "";
//        for (OrganizationEntity org : list)
//        {
//            sb = "{\"name\":\"" + org.getName() + "\",\"id\":\""
//                    + org.getId() + "\",\"level\":\""+org.getLevel()+"\"}";
//            strlist.add(sb);
//        }
      //  String json = "{\"data\":" + strlist.toString() + ",\"length\":"+list.size()+"}";
       // response.setCharacterEncoding("UTF-8");
       // PrintWriter out = response.getWriter();
        //out.print(json);
        //out.flush();
       // out.close();
        return map;
    }
}
