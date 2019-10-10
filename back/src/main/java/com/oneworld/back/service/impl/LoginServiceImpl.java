package com.oneworld.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.dao.LoginDao;
import com.oneworld.back.service.LoginService;
import com.oneworld.back.service.PermissionService;
import com.oneworld.back.utils.CommonUtil;
import com.oneworld.back.utils.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqiao
 * @date 2019/9/17 18:39
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private PermissionService permissionService;
    @Override
    public Map login(String userName, String password) {
        Map info = new HashMap();
        /*Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
        } catch (AuthenticationException e) {
            info.put("result", "fail");
        }*/
        return info;
    }

    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            currentUser.login(token);
            info.put("result", "success");
        } catch (AuthenticationException e) {
            info.put("result", "fail");
        }
        return CommonUtil.successJson(info);
    }
    /**
     * 根据用户名和密码查询对应的用户
     */
    @Override
    public JSONObject getUser(String username, String password) {
        return loginDao.getUser(username, password);
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public JSONObject getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject info = new JSONObject();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        if(userInfo != null){
            if(userInfo.containsKey("username")){
                String username = userInfo.getString("username");
                JSONObject userPermission = permissionService.getUserPermission(username);
                session.setAttribute(Constants.SESSION_USER_PERMISSION, userPermission);
                info.put("userPermission", userPermission);
            }
        }
        return CommonUtil.successJson(info);
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CommonUtil.successJson();
    }
}
