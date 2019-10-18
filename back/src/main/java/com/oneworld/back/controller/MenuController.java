package com.oneworld.back.controller;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.service.MenuService;
import com.oneworld.back.utils.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequiresPermissions("menu:list")
    @GetMapping("/listMenu")
    public JSONObject listArticle(HttpServletRequest request) {
        return menuService.getAllMenu(CommonUtil.request2Json(request));
    }

    @RequiresPermissions("menu:delete")
    @PostMapping("/deleteMenu")
    public JSONObject deleteMenu(@RequestBody JSONObject jsonObject) {
        return menuService.deleteMenu(jsonObject);
    }

    @RequiresPermissions("menu:add")
    @PostMapping("/addPermission")
    public JSONObject addUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "menuCode, menuName, permissionCode,permissionName");
        return menuService.addMenu(requestJson);
    }

    @RequiresPermissions("menu:update")
    @PostMapping("/updatePermission")
    public JSONObject updateUser(@RequestBody JSONObject requestJson) {
        CommonUtil.hasAllRequired(requestJson, "menuCode, menuName, permissionCode,permissionName");
        return menuService.updateMenu(requestJson);
    }
}
