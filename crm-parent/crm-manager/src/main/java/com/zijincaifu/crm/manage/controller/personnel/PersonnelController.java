package com.zijincaifu.crm.manage.controller.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.exception.WebException;
import com.sxj.util.persistent.ResultList;
import com.zijincaifu.crm.entity.personnel.FunctionEntity;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
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
    public String getSysAccountList(PersonnelQuery query, ModelMap map)  throws WebException{
        try
        {
            if (query != null) {
                query.setPagable(true);
            }
            List<PersonnelEntity> list = personneServicel.queryPersonnels(query);
            
//            List<FunctionEntity> functionList = functionService
//                    .queryChildrenFunctions("0");
            map.put("list", list);
//            map.put("functions", functionList);
            map.put("query", query);
            return "manage/personnel/personnelList";
        }
        catch (Exception e)
        {
           throw new WebException("",e);
        }
     

    }
}
