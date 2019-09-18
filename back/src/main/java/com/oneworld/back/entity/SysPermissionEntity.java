package com.oneworld.back.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangqiao
 * @date 2019/9/17 17:26
 */
@Getter
@Setter
public class SysPermissionEntity {
    private Integer id;

    private String menuCode;

    private String menuName;

    private String permissionCode;

    private String permissionName;

    private Integer requiredPermission;
}
