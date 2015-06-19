package com.zijincaifu.crm.manage.controller.personnel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jsoup.helper.StringUtil;
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
import com.zijincaifu.crm.manage.controller.BaseController;
import com.zijincaifu.crm.manage.login.PublishMessage;
import com.zijincaifu.model.personnel.FunctionModel;
import com.zijincaifu.model.personnel.PersonnelQuery;
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
            if (StringUtils.isNotEmpty(query.getCompany()))
            {
                query.setCompanyStr(getLoginInfo().getCompanyStr() + ","
                        + query.getCompany());
            }
            else
            {
                query.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            query.setShowCount(15);
            List<PersonnelEntity> list = personneService.queryPersonnels(query);
            List<OrganizationEntity> orgStrList = new ArrayList<OrganizationEntity>();
            String loginCompayStr = getLoginInfo().getCompanyStr();
            if (StringUtils.isNotEmpty(loginCompayStr))
            {
                String[] companyStrs = loginCompayStr.split(",");
                if (companyStrs.length > 0)
                {
                    for (int i = 0; i < companyStrs.length; i++)
                    {
                        orgStrList.add(personneService.getOrg(companyStrs[i]));
                    }
                }
            }
            List<OrganizationEntity> orgList = personneService.queryOrg(getLoginInfo().getCompany()
                    + "");
            map.put("company", query.getCompany());
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
    
    @RequestMapping("checkUnionId")
    public @ResponseBody Map<String, Object> checkUnionId(String unionId)
            throws WebException
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isCheck", false);
        try
        {
            PersonnelQuery query = new PersonnelQuery();
            query.setUnionId(unionId);
            List<PersonnelEntity> list = personneService.queryPersonnels(query);
            if (list != null && list.size() > 0)
            {
                map.put("isCheck", true);
            }
        }
        catch (Exception e)
        {
            map.put("isCheck", false);
        }
        return map;
    }
    
    @RequestMapping("loadAddPersonnel")
    public String loadAddPersonnel(ModelMap map) throws WebException
    {
        try
        {
            List<OrganizationEntity> orgStrList = new ArrayList<OrganizationEntity>();
            String loginCompayStr = getLoginInfo().getCompanyStr();
            if (StringUtils.isNotEmpty(loginCompayStr))
            {
                String[] companyStrs = loginCompayStr.split(",");
                for (int i = 0; i < companyStrs.length; i++)
                {
                    orgStrList.add(personneService.getOrg(companyStrs[i]));
                }
            }
            
            List<OrganizationEntity> orgList = personneService.queryOrg(getLoginInfo().getCompany()
                    + "");
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
            if (StringUtils.isNotEmpty(personnel.getCompanyStr() + ""))
            {
                personnel.setCompanyStr(getLoginInfo().getCompanyStr() + ","
                        + personnel.getCompanyStr());
            }
            else
            {
                personnel.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            String[] temCom = personnel.getCompanyStr().split(",");
            OrganizationEntity org = personneService.getOrg(temCom[temCom.length - 1]);
            //            personnel.setCompanyStr(companyStr);
            personnel.setCompany(Integer.parseInt(temCom[temCom.length - 1]));
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
            List<OrganizationEntity> orgStrList = new ArrayList<OrganizationEntity>();
            String loginCompayStr = getLoginInfo().getCompanyStr();
            if (StringUtils.isNotEmpty(loginCompayStr))
            {
                String[] companyStrs = loginCompayStr.split(",");
                for (int i = 0; i < companyStrs.length; i++)
                {
                    orgStrList.add(personneService.getOrg(companyStrs[i]));
                }
                map.put("parentStr", getLoginInfo().getCompanyStr());
                if (personnel.getCompanyStr()
                        .equals(getLoginInfo().getCompanyStr()))
                {
                    map.put("ownStr", "false");
                }
                else
                {
                    Set<String> set = new TreeSet<String>();
                    String[] str1 = personnel.getCompanyStr().split(",");
                    String[] str2 = getLoginInfo().getCompanyStr().split(",");
                    if (str1.length > str2.length)
                    {
                        for (int i = 0; i < str1.length; i++)
                        {
                            set.add(str1[i]);
                        }
                        for (int i = 0; i < str2.length; i++)
                        {
                            set.remove(str2[i]);
                        }
                    }
                    else
                    {
                        for (int i = 0; i < str2.length; i++)
                        {
                            set.add(str2[i]);
                        }
                        for (int i = 0; i < str1.length; i++)
                        {
                            set.remove(str1[i]);
                        }
                    }
                    String ownStr = "";
                    int index = 1;
                    for (Iterator<String> iterator = set.iterator(); iterator.hasNext();)
                    {
                        String setvalue = (String) iterator.next();
                        ownStr = ownStr + setvalue;
                        if (index < set.size())
                        {
                            ownStr = ownStr + ",";
                        }
                        index++;
                    }
                    
                    map.put("ownStr", ownStr);
                }
            }
            else
            {
                map.put("parentStr", "");
                map.put("ownStr", "false");
            }
            List<OrganizationEntity> orgList = personneService.queryOrg(getLoginInfo().getCompany()
                    + "");
            map.put("orgStrList", orgStrList);
            map.put("org", orgList);
            List<FunctionEntity> roleList = roleService.getAllRoleFunction(uid);
            List<FunctionModel> allFunction = functionService.queryTreeFunctions();
            map.put("allFunction", allFunction);
            map.put("roleList", roleList);
            map.put("loginUid", getLoginInfo().getUid());
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
            if (StringUtils.isNotEmpty(personnel.getCompanyStr() + ""))
            {
                personnel.setCompanyStr(getLoginInfo().getCompanyStr() + ","
                        + personnel.getCompanyStr());
            }
            else
            {
                personnel.setCompanyStr(getLoginInfo().getCompanyStr());
            }
            String[] temCom = personnel.getCompanyStr().split(",");
            OrganizationEntity org = personneService.getOrg(temCom[temCom.length - 1]);
            
            PersonnelEntity temPer = personneService.getPersonnel(personnel.getUid());
            if (StringUtil.isBlank(temPer.getPhoneStr()))
            {
                personnel.setPhoneStr(personnel.getPhone());
            }
            else
            {
                personnel.setPhoneStr(temPer.getPhoneStr() + ","
                        + temPer.getPhone());
            }
            personnel.setCompany(Integer.parseInt(org.getId()));
            personnel.setCompanyName(org.getName());
            personneService.editPersonnel(personnel, ids);
            if (roles.size() > 0)
            {
                PublishMessage message = new PublishMessage();
                message.setType("update");
                message.setUserId(personnel.getUid());
                message.setRoles(roles);
                topics.getTopic(Constraints.MANAGER_CHANNEL_NAME)
                        .publish(message);
            }
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
        Map<String, Object> map = new HashMap<>();
        List<OrganizationEntity> list = personneService.queryOrg(parentId);
        map.put("list", list);
        return map;
    }
    
    //    @RequestMapping("downloadPfx")
    //    public void createPfx(HttpServletResponse response)
    //            throws KeyPairException, StorageException, CertificateException
    //    {
    //        try
    //        {
    //            String uid = getLoginInfo().getUid();
    //            String name = getLoginInfo().getName();
    //            ServletOutputStream output = response.getOutputStream();
    //            response.addHeader("Content-Disposition", "attachment;filename="
    //                    + name + "_employee.pfx");
    //            response.setContentType("application/pfx");
    //            
    //            CAManager ca = new CAManager();
    //            PEMFileStore<X509Certificate> certstore = new PEMFileStore<X509Certificate>(
    //                    "D:\\certs\\ca.crt");
    //            PEMFileStore<X509Certificate> clientcertstore = new PEMFileStore<X509Certificate>(
    //                    "D:\\certs\\client.crt");
    //            PEMFileStore<KeyPair> clientkeystore = new PEMFileStore<KeyPair>(
    //                    "D:\\certs\\client.key");
    //            PEMFileStore<PublicKey> publicstore = new PEMFileStore<PublicKey>(
    //                    "D:\\certs\\" + name + "_employee.pub");
    //            PEMFileStore<KeyPair> employeekeystore = new PEMFileStore<KeyPair>(
    //                    "D:\\certs\\" + name + "_employee.key");
    //            PEMFileStore<PKCS10CertificationRequest> employeerequeststore = new PEMFileStore<PKCS10CertificationRequest>(
    //                    "D:\\certs\\" + name + "_employee.req");
    //            PEMFileStore<X509Certificate> employeecertstore = new PEMFileStore<X509Certificate>(
    //                    "D:\\certs\\" + name + "_employee.crt");
    //            
    //            KeyPair keypair = KeyPairManager.generateRSAKeyPair();
    //            PublicKey public1 = keypair.getPublic();
    //            publicstore.save(public1, null);
    //            
    //            employeekeystore.save(keypair, null);
    //            X509Attrs principals = new X509Attrs();
    //            principals.setCommonName(name);
    //            principals.setCountryCode("AU");
    //            principals.setGiveName(uid);
    //            PKCS10CertificationRequest csr = CSRManager.generateCSR(keypair,
    //                    principals);
    //            
    //            employeerequeststore.save(csr, null);
    //            
    //            PKCS10CertificationRequest request = employeerequeststore.read();
    //            X509Certificate parentcert = clientcertstore.read();
    //            KeyPair parentkey = clientkeystore.read();
    //            X509Certificate certificate = ca.issueCertificate(request,
    //                    365,
    //                    parentcert,
    //                    parentkey,
    //                    false);
    //            employeecertstore.save(certificate, null);
    //            
    //            PEMFileStore<X509Certificate> intercertstore = new PEMFileStore<X509Certificate>(
    //                    "D:\\certs\\inter.crt");
    //            PEMFileStore<X509Certificate> servercertstore = new PEMFileStore<X509Certificate>(
    //                    "D:\\certs\\server.crt");
    //            X509Certificate cacert = certstore.read();
    //            X509Certificate intercert = intercertstore.read();
    //            X509Certificate servercert = servercertstore.read();
    //            X509Certificate clientcert = clientcertstore.read();
    //            X509Certificate employeecert = employeecertstore.read();
    //            X509Certificate[] chain = new X509Certificate[5];
    //            chain[0] = (employeecert);
    //            chain[1] = (clientcert);
    //            chain[2] = (servercert);
    //            chain[3] = (intercert);
    //            chain[4] = (cacert);
    //            KeyPair employeekey = new PEMFileStore<KeyPair>("D:\\certs\\"
    //                    + name + "_employee.key").read();
    //            KeyStore pkcs12 = ca.generatePKCS12(chain, employeekey);
    //            PfxStore pdxStore = new PfxStore("D://certs//" + name
    //                    + "_employee.pfx");
    //            pdxStore.save(pkcs12, "123456");
    //            byte[] bytes = FileUtil.readBytes("D://certs//" + name
    //                    + "_employee.pfx");
    //            output.write(bytes);
    //            output.flush();
    //            output.close();
    //        }
    //        catch (Exception e)
    //        {
    //            e.printStackTrace();
    //        }
    //        
    //    }
}
