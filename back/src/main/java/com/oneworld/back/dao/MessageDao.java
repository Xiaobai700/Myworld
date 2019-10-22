package com.oneworld.back.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/22 9:20
 */
public interface MessageDao {
    /**
     * 查询消息列表
     * */
    List<JSONObject> getMessageList(JSONObject jsonObject);
}
