package com.zijincaifu.crm.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.util.comet.CometServiceImpl;
import com.sxj.util.exception.WebException;
import com.sxj.util.logger.SxjLogger;
import com.zijincaifu.crm.manage.login.SupervisorShiroRedisCache;
import com.zijincaifu.entity.personnel.FunctionEntity;
import com.zijincaifu.entity.personnel.PersonnelEntity;

@Controller
public class BasicController extends BaseController
{
    
    private static final HttpServletRequest DefaultMultipartHttpServletRequest = null;
    
    //    @Autowired
    //    private IRoleService roleService;
    //    
    //    @Autowired
    //    private ISystemAccountService accountService;
    //    
    //    @Autowired
    //    private IFunctionService functionService;
    //    
    //    @Autowired
    //    private IMemberService memberService;
    //    
    //    @Autowired
    //    private IRfidSupplierService supplierService;
    //    
    //    @Autowired
    //    private IStorageClientService storageClientService;
    //    
    //    @Autowired
    //    private IQueryOperation operatorService;
    
    @Autowired
    private RedisTopics topics;
    
    //    @Autowired
    //    private IDevelopersService developerService;
    
    //    @PostConstruct
    //    public void init()
    //    {
    //        CometEngine engine = CometContext.getInstance().getEngine();
    //        // 启动 Comet Server Thread
    //        MessageThread cometServer = MessageThread.newInstance(engine);
    //        RTopic<String> topic1 = topics.getTopic("topic1");
    //        topic1.addListener(new CometMessageListener(cometServer));
    //    }
    
    @RequestMapping("notifyComet")
    public @ResponseBody void notifyComet(String channelName)
    {
        CometServiceImpl.sendMessage("topic1", channelName);
    }
    
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
            PersonnelEntity newAccount = accountService.getAccountByAccount(userName.getAccount());
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
        PersonnelEntity user = accountService.getAccountByAccount(account);
        if (user == null)
        {
            map.put("message", "用户名不存在");
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
            String accountNo = user.getAccountNo();
            SupervisorShiroRedisCache.addToMap(accountNo, principals);
        }
        catch (AuthenticationException e)
        {
            map.put("account", account);
            map.put("message", "用户名或密码错误");
            return LOGIN;
            
        }
        if (currentUser.isAuthenticated())
        {
            session.setAttribute("userinfo", user);
            user.setLastLogin(new Date());
            accountService.updateLoginTime(user.getId());
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
    
    /**
     * 左侧菜单
     * 
     * @param map
     * @return
     */
    @RequestMapping("menu")
    public String toMenu(ModelMap map)
    {
        Subject user = SecurityUtils.getSubject();
        PersonnelEntity userName = (PersonnelEntity) user.getPrincipal();
        if (userName == null)
        {
            return LOGIN;
        }
        List<FunctionModel> list = roleService.getRoleFunction(userName.getId());
        map.put("list", list);
        return "manage/menu";
    }
    
    @RequestMapping("menu_path")
    public String menuPath(HttpServletRequest request, ModelMap map)
            throws WebException
    {
        try
        {
            HttpSession session = request.getSession(false);
            if (session.getAttribute("function") != null)
            {
                String functionId = (String) session.getAttribute("function");
                FunctionEntity function = functionService.getFunction(functionId);
                if (function != null)
                {
                    if (!function.getParentId().equals("0"))
                    {
                        FunctionEntity parent = functionService.getFunction(function.getParentId());
                        map.put("parentTitle", parent.getTitle());
                    }
                    map.put("title", function.getTitle());
                }
            }
            return "manage/menu-path";
        }
        catch (Exception e)
        {
            SxjLogger.error(e.getMessage(), e, this.getClass());
            throw new WebException("系统错误");
        }
    }
    
    private String stringDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份         
        int month = cal.get(Calendar.MONTH);//获取月份         
        int day = cal.get(Calendar.DATE);//获取日         
        int hour = cal.get(Calendar.HOUR);//小时          
        int minute = cal.get(Calendar.MINUTE);//分                     
        int second = cal.get(Calendar.SECOND);//秒     
        String date = year + "" + month + "" + day + "" + hour + "" + minute
                + "" + second;
        
        return date;
    }
    
    /**
     * 自动感应会员
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoComple")
    public @ResponseBody Map<String, String> autoComple(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        List<MemberEntity> list = memberService.queryMembers(mq);
        List<String> strlist = new ArrayList<String>();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
            sb = "{\"title\":\"" + memberEntity.getName() + "\",\"result\":\""
                    + memberEntity.getMemberNo() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
    public static String getIpAddr(HttpServletRequest request)
    {
        String strUserIp = null;
        /** * */
        // Apache 代理 解决IP地址问题
        strUserIp = request.getHeader("X-Forwarded-For");
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getHeader("Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (strUserIp == null || strUserIp.length() == 0
                || "unknown".equalsIgnoreCase(strUserIp))
        {
            strUserIp = request.getRemoteAddr();
        }
        // 解决获取多网卡的IP地址问题
        if (strUserIp != null)
        {
            strUserIp = strUserIp.split(",")[0];
        }
        else
        {
            strUserIp = "127.0.0.1";
        }
        // 解决获取IPv6地址 暂时改为本机地址模式
        if (strUserIp.length() > 16)
        {
            strUserIp = "127.0.0.1";
        }
        return strUserIp;
        // 没有IP Apache 代理 解决IP地址问题
        // strUserIp=request.getRemoteAddr();
        // if (strUserIp != null) {strUserIp = strUserIp.split(",")[0];}
        // return strUserIp;
    }
    
    @RequestMapping("enter")
    @ResponseBody
    public void enter(HttpSession session, HttpServletRequest request,
            String url)
    {
        Date enterTime = (Date) session.getAttribute("enterTime");
        String currentUrl = (String) session.getAttribute("currentUrl");
        String nextUrl = (String) session.getAttribute("nextUrl");
        if (currentUrl == null)
        {
            session.setAttribute("currentUrl", request.getHeader("Referer"));
            
        }
        session.setAttribute("previousUrl", currentUrl);
        session.setAttribute("currentUrl", nextUrl);
        session.setAttribute("nextUrl", url);
        
        if (enterTime != null)
        {
            SystemAccountEntity account = getLoginInfo(session);
            OperatorLogEntity log = new OperatorLogEntity();
            log.setAccountNo(account.getAccountNo());
            log.setOperatorTime(new Date());
            log.setLogs("Entering page" + session.getAttribute("previousUrl")
                    + "------current:    " + session.getAttribute("currentUrl")
                    + "------next:     " + session.getAttribute("nextUrl"));
            operatorService.addOperatorLog(log);
        }
        session.setAttribute("enterTime", new Date());
        
    }
    
    /**
     * 甲方联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleA")
    public @ResponseBody Map<String, String> autoCompleA(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        mq.setMemberType(0);
        List<MemberEntity> list = memberService.queryMembers(mq);
        List strlist = new ArrayList();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
            sb = "{\"title\":\"" + memberEntity.getName() + "\",\"result\":\""
                    + memberEntity.getMemberNo() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
    /**
     * 乙方联想
     * 
     * @param request
     * @param response
     * @param keyword
     * @return
     * @throws IOException
     */
    @RequestMapping("autoCompleB")
    public @ResponseBody Map<String, String> autoCompleB(
            HttpServletRequest request, HttpServletResponse response,
            String keyword) throws IOException
    {
        MemberQuery mq = new MemberQuery();
        if (keyword != "" && keyword != null)
        {
            mq.setMemberName(keyword);
        }
        mq.setMemberTypeB(0);
        List<MemberEntity> list = memberService.queryMembers(mq);
        List strlist = new ArrayList();
        String sb = "";
        for (MemberEntity memberEntity : list)
        {
            sb = "{\"title\":\"" + memberEntity.getName() + "\",\"result\":\""
                    + memberEntity.getMemberNo() + "\"}";
            strlist.add(sb);
        }
        String json = "{\"data\":" + strlist.toString() + "}";
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
        return null;
    }
    
}
