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
    /**
     * 统计每一个菜单下面的权限数
     * */
    List<Integer> countPermissionEachMenu();

    /**
     * 获取菜单列表
     * */
    List<JSONObject> getMenuList(JSONObject jsonObject);

    /**
     * 删除这个权限（逻辑删除）
     * */
    int deleteMenu(JSONObject jsonObject);
}
