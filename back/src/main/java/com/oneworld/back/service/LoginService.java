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


}
