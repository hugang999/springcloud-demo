package com.hugang.shiro.config;


import com.hugang.shiro.realms.UserRealm;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro相关配置
 */
@Configuration
public class ShiroConfig {


    /**
     * 创建ShiroFilterFactoryBean
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //设置安全管理器
        shiroFilter.setSecurityManager(securityManager);

        //添加shiro内置过滤器，可以实现权限的拦截
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/shiro/login", "anon");
        filterMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        //设置登录路径
        shiroFilter.setLoginUrl("/shiro/login");
        return shiroFilter;
    }

    /**
     * 创建DefaultSecurityManager
     * @param userRealm
     * @return
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        return  securityManager;

    }


    /**
     * 创建Realm
     * @return
     */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }
}
