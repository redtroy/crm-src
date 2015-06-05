package com.zijincaifu.crm.manage.controller.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.entity.customer.TrackRecordEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.service.customer.ITrackRecordService;

@Controller
@RequestMapping("/customer/track")
public class TrackRecordController extends BaseController
{
    @Autowired
    private ITrackRecordService trackRecordService;
    
    @RequestMapping("/query")
    public String query(String customerId, ModelMap map) throws WebException
    {
        try
        {
            List<TrackRecordEntity> list = trackRecordService.query(customerId);
            map.put("list", list);
            map.put("customerId", customerId);
            return "manage/customer/trackRecord";
        }
        catch (Exception e)
        {
            SxjLogger.error("查询跟踪记录信息错误", e, this.getClass());
            throw new WebException("查询跟踪记录信息错误", e);
        }
    }
    
    @RequestMapping("addTrackRecord")
    public @ResponseBody Map<String, Object> addTrackRecord(
            TrackRecordEntity trackRecord) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            trackRecord.setCreateTime(new Date());
            trackRecordService.addTrackRecord(trackRecord);
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
}
