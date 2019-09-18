package com.oneworld.back.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class SysUserEntity{
	private Integer id;

	private String userName;

	private String nickName;

	private String password;

	private Integer roleId;

	private Date createTime;

	private Date updateTime;

	private String deleteStatus;
}
