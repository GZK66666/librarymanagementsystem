package com.scu.librarymanagementsystem.config.shiro;

import com.scu.librarymanagementsystem.common.enums.UserType;
import com.scu.librarymanagementsystem.model.User;
import com.scu.librarymanagementsystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录对象
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        //设置角色
        Set<String> roles = new HashSet<>();
        roles.add(user.getUserType().equals(UserType.ADMIN)?"admin":"user");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);

        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        List<User> user = userService.findUsersByMultiConditions(token.getUsername(), null);
        if (user != null && !user.isEmpty()) {
            return new SimpleAuthenticationInfo(user.get(0), user.get(0).getPassword(), getName());
        }

        return null;
    }
}
