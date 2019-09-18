package com.oneworld.back.config.shiro;

import com.oneworld.back.dao.SysPermissionDao;
import com.oneworld.back.dao.SysUserDao;
import com.oneworld.back.entity.SysUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zhangqiao
 * @date 2019/9/17 17:11
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysPermissionDao sysPermissionDao;
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUserEntity userEntity =(SysUserEntity) principalCollection.getPrimaryPrincipal();
        int id = userEntity.getId();

        List<String> permsList;

        if(id == 1){
            //如果是超级管理员就拥有所有权限
            permsList = sysPermissionDao.selectAllPermission();
        }else {
            permsList = sysPermissionDao.selectPermissionByUserId(id);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String userName = token.getUsername();
        SysUserEntity userEntity = sysUserDao.selectUserByUserName(userName);
        if(userEntity == null){
            throw new UnknownAccountException("账号或密码不正确");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userEntity, userEntity.getPassword(), getName());
        return info;
    }
}
