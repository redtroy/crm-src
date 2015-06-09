package com.zijincaifu.crm.manage.login;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import com.sxj.redis.core.RMap;
import com.sxj.redis.core.collections.RedisCollections;
import com.sxj.spring.modules.security.shiro.ShiroRedisCacheManager;
import com.sxj.util.Constraints;
import com.zijincaifu.crm.entity.personnel.PersonnelEntity;

public class SupervisorManagerPermissionsAuthorizationFilter extends
        PermissionsAuthorizationFilter
{
    
    private RedisCollections collections;
    
    private static ShiroRedisCacheManager cacheManager;
    
    @Override
    public boolean isAccessAllowed(ServletRequest request,
            ServletResponse response, Object mappedValue) throws IOException
    {
        
        boolean permitted = false;
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() == null)
        {
            return permitted;
        }
        PersonnelEntity user = (PersonnelEntity) subject.getPrincipal();
        
        RMap<Object, Object> map = collections.getMap(Constraints.SHIRO_MAP_KEY);
        if (map == null)
        {
            return permitted;
        }
        if (map.get(user.getUid()) == null)
        {
            return permitted;
        }
        //        List<PrincipalCollection> principals = (List<PrincipalCollection>) map.get(user.getUid());
        //        String cacheName = CustomizedPropertyPlaceholderConfigurer.getContextProperty("crm.manage.authorization.cache.name");
        //        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        //        int index = 0;
        //        for (PrincipalCollection principal : principals)
        //        {
        //            Object object = cache.get(principal);
        //            if (object == null)
        //            {
        //                index++;
        //            }
        //            
        //        }
        //        if (index > 0)
        //        {
        //            subject.logout();
        //        }
        return true;
        
    }
    
    public RedisCollections getCollections()
    {
        return collections;
    }
    
    public void setCollections(RedisCollections collections)
    {
        this.collections = collections;
    }
    
    public static ShiroRedisCacheManager getCacheManager()
    {
        return cacheManager;
    }
    
    public static void setCacheManager(ShiroRedisCacheManager cacheManager)
    {
        SupervisorManagerPermissionsAuthorizationFilter.cacheManager = cacheManager;
    }
    
}
