package com.zijincaifu.crm.manage.controller.personnel;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.persistent.ResultList;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.entity.personnel.FunctionEntity;
import com.zijincaifu.entity.personnel.PersonnelEntity;
import com.zijincaifu.model.personnel.PersonnelQuery;
import com.zijincaifu.service.personnel.IPersonnelService;

@Controller
@RequestMapping("/personnel")
public class PersonnelController extends BaseController
{
    @Autowired
    private IPersonnelService personneServicel;
    
    @RequestMapping("personnelList")
    public String getSysAccountList(PersonnelQuery query, ModelMap map) {
        if (query != null) {
            query.setPagable(true);
        }
        ResultList<PersonnelEntity> list = personneServicel
                .queryPersonnels(query);
//        List<FunctionEntity> functionList = functionService
//                .queryChildrenFunctions("0");
        map.put("list", list.getResults());
//        map.put("functions", functionList);
        map.put("query", query);
        return "manage/personnel/personnelList";

    }
}
