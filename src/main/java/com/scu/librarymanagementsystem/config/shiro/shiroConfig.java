package com.scu.librarymanagementsystem.config.shiro;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class shiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置URL拦截规则
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
//        filterChainDefinitionMap.put("/api/Users/add", "authc");
        filterChainDefinitionMap.put("/api/Users/delete", "roles[admin]");
        filterChainDefinitionMap.put("/api/Users/updateUser", "roles[admin]");
        filterChainDefinitionMap.put("/api/Users/findUsers", "authc");
        filterChainDefinitionMap.put("/api/Books/add", "roles[admin]");
        filterChainDefinitionMap.put("/api/Books/delete", "roles[admin]");
        filterChainDefinitionMap.put("/api/Books/updateBook", "roles[admin]");
        filterChainDefinitionMap.put("/api/Books/findBooks", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        shiroFilterFactoryBean.setLoginUrl("/index/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/api/Users/unAuth");

        return shiroFilterFactoryBean;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public Realm realm() {
        return new CustomRealm();
    }
}
