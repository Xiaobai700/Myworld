package com.oneworld.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.dao.ErrorCodeDao;
import com.oneworld.back.service.ErrorCodeService;
import com.oneworld.back.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/22 13:09
 */
@Service
public class ErrorCodeServiceImpl implements ErrorCodeService {
    @Autowired
    private ErrorCodeDao errorCodeDao;

    @Override
    public JSONObject listErrorCode(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = errorCodeDao.countErrorCode(jsonObject);
        List<JSONObject> list = errorCodeDao.listErrorCode(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }

    @Override
    public JSONObject saveErrorCode(JSONObject jsonObject) {
        errorCodeDao.addErrorCode(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject updateErrorCode(JSONObject jsonObject) {
        errorCodeDao.updateErrorCode(jsonObject);
        return CommonUtil.successJson();
    }

    @Override
    public JSONObject deleteErrorCodes(Long[] deleteArr) {
        errorCodeDao.deleteErrorCodes(deleteArr);
        return CommonUtil.successJson();
    }

    @Override
    public List<JSONObject> selectAll() {
        return errorCodeDao.selectAll();
    }
}
