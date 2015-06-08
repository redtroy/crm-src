package com.zijincaifu.crm.manage.login;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import com.sxj.redis.core.MessageListener;
import com.sxj.redis.core.RMap;
import com.sxj.redis.core.RTopic;
import com.sxj.redis.core.collections.RedisCollections;
import com.sxj.redis.core.pubsub.RedisTopics;
import com.sxj.spring.modules.beanfactory.CustomizedPropertyPlaceholderConfigurer;
import com.sxj.spring.modules.security.shiro.ShiroRedisCacheManager;
import com.sxj.util.Constraints;

public class ShiroEventListener implements BeanFactoryPostProcessor
{
    
    private static RedisTopics topics;
    
    private static RedisCollections collections;
    
    private static ShiroRedisCacheManager cacheManager;
    
    // private static DefaultWebSessionManager sessionManager;
    
    private static String cacheName;
    
    @Override
    public void postProcessBeanFactory(
            final ConfigurableListableBeanFactory beanFactory)
            throws BeansException
    {
        topics = beanFactory.getBean(RedisTopics.class);
        collections = beanFactory.getBean(RedisCollections.class);
        cacheManager = beanFactory.getBean(SupervisorShiroRedisCacheManager.class);
        cacheName = CustomizedPropertyPlaceholderConfigurer.getContextProperty("crm.manage.authorization.cache.name");
        // sessionManager = beanFactory.getBean(DefaultWebSessionManager.class);
        RTopic<Object> topic = topics.getTopic(Constraints.MANAGER_CHANNEL_NAME);
        topic.addListener(new MessageListener<Object>()
        {
            @Override
            public void onMessage(Object msg)
            {
                if (!(msg instanceof PublishMessage))
                {
                    return;
                }
                PublishMessage message = (PublishMessage) msg;
                String type = message.getType();
                String userId = message.getUserId();
                Set<String> roles = message.getRoles();
                RMap<Object, Object> map = collections.getMap(Constraints.SHIRO_MAP_KEY);
                if (map == null)
                {
                    return;
                }
                if (map.get(userId) == null)
                {
                    return;
                }
                List<PrincipalCollection> principals = (List<PrincipalCollection>) map.get(userId);
                Cache<Object, Object> cache = cacheManager.getCache(cacheName);
                for (PrincipalCollection principal : principals)
                {
                    if ("del".equals(type))
                    {
                        map.remove(principal);
                    }
                    else if ("update".equals(type))
                    {
                        SimpleAuthorizationInfo old = (SimpleAuthorizationInfo) cache.get(principal);
                        if (old == null)
                        {
                            continue;
                        }
                        old.setRoles(roles);
                        cache.put(principal, old);
                    }
                    
                }
                
            }
        });
        
    }
}
