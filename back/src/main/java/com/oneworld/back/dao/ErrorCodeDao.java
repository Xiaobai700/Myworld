package com.oneworld.back.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/22 13:12
 */
public interface ErrorCodeDao {
    /**
     * 错误码列表
     */
    List<JSONObject> listErrorCode(JSONObject jsonObject);

    /**
     * 统计错误码总数
     * */
    int countErrorCode(JSONObject jsonObject);

    /**
     * 新增错误码
     */
    int addErrorCode(JSONObject jsonObject);

    /**
     * 更新错误码
     * */
    int updateErrorCode(JSONObject jsonObject);

    /**
     * 删除错误码
     * */
    int deleteErrorCodes(Long[] deleteArr);

    /**
     * 查询所有错误码
     * */
    List<JSONObject> selectAll();
}
