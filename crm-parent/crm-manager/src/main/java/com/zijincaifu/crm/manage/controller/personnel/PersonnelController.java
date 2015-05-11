package com.zijincaifu.crm.manage.controller.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.exception.WebException;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.enu.personnel.PersonnelCompanyEnum;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IPersonnelService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController
{
    @Autowired
    private IPersonnelService personneServicel;
    
    @RequestMapping("personnelList")
    public String getPersonnelList(PersonnelQuery query, ModelMap map)  throws WebException{
        try
        {
            if (query != null) {
                query.setPagable(true);
            }
            List<PersonnelEntity> list = personneServicel.queryPersonnels(query);
            PersonnelCompanyEnum[] company=PersonnelCompanyEnum.values();
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
           throw new WebException("",e);
        }
     

    }
    
    @RequestMapping("personnelAdd")
    public String addPersonnel(ModelMap map)  throws WebException{
        try
        {
            PersonnelCompanyEnum[] company=PersonnelCompanyEnum.values();
            map.put("company", company);
            return "manage/personnel/personnelAdd";
        }
        catch (Exception e)
        {
           throw new WebException("",e);
        }
     

    }
}
