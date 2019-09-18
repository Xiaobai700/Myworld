package com.oneworld.back.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangqiao
 * @date 2019/9/17 17:42
 */
public interface SysPermissionDao{
    List<String> selectAllPermission();

    List<String> selectPermissionByUserId(@Param("userId") Integer userId);
}
