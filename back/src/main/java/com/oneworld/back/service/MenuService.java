package com.oneworld.back.service;

import com.alibaba.fastjson.JSONObject;


/**
 * @author zhangqiao
 * @date 2019/10/11 17:16
 */
public interface MenuService {
    JSONObject getAllMenu(JSONObject jsonObject);

    JSONObject deleteMenu(JSONObject jsonObject);

    JSONObject addMenu(JSONObject jsonObject);

    JSONObject updateMenu(JSONObject jsonObject);

    JSONObject getUserPermission(JSONObject jsonObject);

}
