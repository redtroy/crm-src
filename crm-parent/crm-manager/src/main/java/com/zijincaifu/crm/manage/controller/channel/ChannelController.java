package com.zijincaifu.crm.manage.controller.channel;

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
import com.zijincaifu.crm.entity.channel.ChannelEntity;
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.model.channel.ChannelModel;
import com.zijincaifu.model.channel.ChannelQuery;
import com.zijincaifu.service.channel.IChannelService;

@Controller
@RequestMapping("/channel")
public class ChannelController extends BaseController
{
    @Autowired
    private IChannelService channelService;
    
    @RequestMapping("channelList")
    public String getChannelList(ChannelQuery query, ModelMap map)
            throws WebException
    {
        try
        {
            if (query != null)
            {
                query.setPagable(true);
            }
            List<ChannelModel> list = channelService.queryChannels(query);
//            PersonnelCompanyEnum[] company = PersonnelCompanyEnum.values();
            //            List<FunctionEntity> functionList = functionService
            //                    .queryChildrenFunctions("0");
            map.put("list", list);
//            map.put("company", company);
//            map.put("functions", functionList);
            map.put("query", query);
            return "manage/channel/channelList";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
        
    }
    
    @RequestMapping("loadAddChannel")
    public String loadAddChannel(ModelMap map) throws WebException
    {
        try
        {
            return "manage/channel/channelAdd";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("addChannel")
    public @ResponseBody Map<String, Object> addChannel(
            ChannelEntity channel) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            channelService.addChannel(channel);
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
    
    @RequestMapping("loadEditChannel")
    public String loadEditChannel(String channelId,ModelMap map) throws WebException
    {
        try
        {
            ChannelModel channel=channelService.getChannel(channelId);
            map.put("channel", channel);
            return "manage/channel/channelEdit";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("error", e.getMessage());
            throw new WebException("", e);
        }
    }
    
    @RequestMapping("editChannel")
    public @ResponseBody Map<String, Object> editChannel(
            ChannelEntity channel) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            channelService.editChannel(channel);
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
    
    @RequestMapping("deleteChannel")
    public @ResponseBody Map<String, Object> deleteChannel(String channelId) throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        try
        {
            ChannelModel cm=channelService.getChannel(channelId);
            channelService.deleteProduct(cm.getId());
            map.put("isOK", "delete");
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            map.put("isOK", "error");
            map.put("error", e.getMessage());
        }
        return map;
    }
}
