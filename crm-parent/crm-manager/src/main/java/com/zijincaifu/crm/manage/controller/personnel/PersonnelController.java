package com.zijincaifu.crm.manage.controller.personnel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.util.common.DateTimeUtils;
import com.sxj.util.common.NumberUtils;
import com.sxj.util.common.StringUtils;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.enu.personnel.PersonnelCompanyEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.personnel.FunctionModel;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IFunctionService;
import com.zijincaifu.service.personnel.IPersonnelService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController
{
    @Autowired
    private IPersonnelService personneService;
    
    @Autowired
    private IFunctionService functionService;
    
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
            List<PersonnelEntity> list = personneService.queryPersonnels(query);
            PersonnelCompanyEnum[] company = PersonnelCompanyEnum.values();
            //            List<FunctionEntity> functionList = functionService
            //                    .queryChildrenFunctions("0");
            map.put("list", list);
            map.put("company", company);
            //            map.put("functions", functionList);
            map.put("query", query);
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
            PersonnelCompanyEnum[] company = PersonnelCompanyEnum.values();
            map.put("company", company);
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
            personnel.setFreezeStatus(1);
            personnel.setAddTime(DateTimeUtils.getCurrentLocaleTime());
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
            PersonnelCompanyEnum[] company = PersonnelCompanyEnum.values();
            map.put("company", company);
            map.put("personnel", personnel);
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
            PersonnelEntity personnel) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            //            personnel.setFreezeStatus(1);
            //            personnel.setAddTime(DateTimeUtils.getCurrentLocaleTime());
            personneService.editPersonnel(personnel);
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
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", false);
            map.put("error", e.getMessage());
        }
        return map;
    }
}
