package com.oneworld.back.dao;

import com.oneworld.back.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author zhangqiao
 * @date 2019/9/17 18:15
 */
public interface SysUserDao {
    SysUserEntity selectUserByUserName(@Param("userName") String userName);
}
