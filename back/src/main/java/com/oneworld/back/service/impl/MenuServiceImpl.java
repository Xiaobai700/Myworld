package com.oneworld.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.dao.MenuDao;
import com.oneworld.back.service.MenuService;
import com.oneworld.back.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/10/11 17:18
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Override
    public JSONObject getAllMenu(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = menuDao.countMenus(jsonObject);
        List<JSONObject> list = menuDao.getMenuList(jsonObject);
        return CommonUtil.successPage(jsonObject, list, count);
    }
}
