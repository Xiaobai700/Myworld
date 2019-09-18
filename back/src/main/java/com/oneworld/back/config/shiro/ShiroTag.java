package com.oneworld.back.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Shiro权限标签
 * @author jinfuming
 * @Time：2018年7月12日 上午8:53:57
 * @version 1.0  
 */
@Component
public class ShiroTag {

	/**
	 * 是否拥有该权限
	 * @param permission  权限标识
	 * @return   true：是     false：否
	 */
	public boolean hasPermission(String permission) {
		Subject subject = SecurityUtils.getSubject();
		return subject != null && subject.isPermitted(permission);
	}

}
