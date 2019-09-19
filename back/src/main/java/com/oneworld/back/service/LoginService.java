package com.oneworld.back.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author zhangqiao
 * @date 2019/9/17 18:39
 */
public interface LoginService {
    Map login(String userName,String password);

    JSONObject authLogin(JSONObject jsonObject);
    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */
    JSONObject getUser(String username, String password);

    /**
     * 查询当前登录用户的权限等信息
     */
    JSONObject getInfo();

    /**
     * 退出登录
     */
    JSONObject logout();


}
