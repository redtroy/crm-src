package com.zijincaifu.crm.manage.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zijincaifu.crm.entity.personnel.PersonnelEntity;
import com.zijincaifu.crm.manage.login.SupervisorShiroRedisCache;
import com.zijincaifu.service.personnel.IPersonnelService;

@Controller
public class BasicController extends BaseController
{
    @Autowired
    private IPersonnelService accountService;
    
    @RequestMapping("footer")
    public String ToFooter()
    {
        return "manage/footer";
    }
    
    @RequestMapping("head")
    public String ToHeader()
    {
        return "manage/head";
    }
    
    @RequestMapping("error")
    public String ToError()
    {
        return "manage/500";
    }
    
    @RequestMapping("404")
    public String To404()
    {
        return "manage/404";
    }
    
    @RequestMapping("index")
    public String ToIndex()
    {
        Subject user = SecurityUtils.getSubject();
        PersonnelEntity userName = (PersonnelEntity) user.getPrincipal();
        if (userName == null)
        {
            return LOGIN;
        }
        else
        {
            PersonnelEntity newAccount = accountService.getPersonnel(userName.getUid());
            if (newAccount == null)
            {
                return LOGIN;
            }
            return INDEX;
        }
    }
    
    @RequestMapping("to_login")
    public String ToLogin()
    {
        return LOGIN;
    }
    
    @RequestMapping("login")
    public String login(String account, String password, HttpSession session,
            HttpServletRequest request, ModelMap map)
    {
        PersonnelEntity user = accountService.getPersonnel(account);
        if (user == null)
        {
            map.put("message", "员工不存在");
            return LOGIN;
        }
        if (user.getFreezeStatus() == 0)
        {
            map.put("message", "员工已冻结");
            return LOGIN;
        }
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account,
                password);
        try
        {
            currentUser.login(token);
            PrincipalCollection principals = SecurityUtils.getSubject()
                    .getPrincipals();
            String uId = user.getUid();
            SupervisorShiroRedisCache.addToMap(uId, principals);
        }
        catch (AuthenticationException e)
        {
            map.put("account", account);
            map.put("message", "员工号或密码错误");
            return LOGIN;
            
        }
        if (currentUser.isAuthenticated())
        {
            //session.setAttribute("userinfo", user);
            user.setLastLoginTime(new Date());
            accountService.editPersonnel(user);
            return "redirect:" + getBasePath(request) + "index.htm";
        }
        else
        {
            map.put("account", account);
            map.put("message", "登陆失败");
            return LOGIN;
        }
    }
    
    @RequestMapping("logout")
    public String logout(HttpServletRequest request)
    {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        return "redirect:" + getBasePath(request) + "to_login.htm";
    }
    
}
