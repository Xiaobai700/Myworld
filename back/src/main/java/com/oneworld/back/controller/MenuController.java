package com.oneworld.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.service.MenuService;
import com.oneworld.back.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqiao
 * @date 2019/10/11 17:24
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    //@RequiresPermissions("article:list")
    @GetMapping("/listMenu")
    public JSONObject listArticle(HttpServletRequest request) {
        System.out.println(menuService.getAllMenu(CommonUtil.request2Json(request)));
        return menuService.getAllMenu(CommonUtil.request2Json(request));
    }
}
