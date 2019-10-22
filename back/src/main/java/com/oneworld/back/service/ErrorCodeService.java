package com.oneworld.back.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/22 13:09
 */
public interface ErrorCodeService {
    /**
     * 错误码列表
     */
    JSONObject listErrorCode(JSONObject jsonObject);

    /**
     * 新建错误码
     * */
    JSONObject saveErrorCode(JSONObject jsonObject);

    /**
     * 更新错误码
     * */
    JSONObject updateErrorCode(JSONObject jsonObject);

    /**
     * 批量删除错误码
     * */
    JSONObject deleteErrorCodes(Long[] deleteArr);

    /**
     * 查询所有错误码
     * */
    List<JSONObject> selectAll();
}
