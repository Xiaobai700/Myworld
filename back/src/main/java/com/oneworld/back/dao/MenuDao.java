package com.oneworld.back.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/11 17:06
 */
public interface MenuDao {
    /**
     * 统计总数
     */
    int countMenus(JSONObject jsonObject);

    List<JSONObject> getMenuList(JSONObject jsonObject);
}
