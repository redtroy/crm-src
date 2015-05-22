package com.zijincaifu.crm.manage.controller.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.customer.RecommendEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.service.customer.IRecommendService;

@Controller
@RequestMapping("/recommend")
public class RecommendController extends BaseController
{
    @Autowired
    private IRecommendService recommendService;
    
    @RequestMapping("/query")
    public String query(String investId, ModelMap map) throws WebException
    {
        try
        {
            List<RecommendEntity> list = recommendService.query(investId);
            map.put("isData", false);
            if (list != null && list.size()> 0)
            {
                map.put("isData", true);
                map.put("list", list);
            }
           
            return "manage/customer/recommend";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
}
