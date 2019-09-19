package com.oneworld.back.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author: hxy
 * @date: 2017/10/24 16:06
 */
public interface ArticleService {
	/**
	 * 新增文章
	 */
	JSONObject addArticle(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	JSONObject listArticle(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	JSONObject updateArticle(JSONObject jsonObject);

	/**
	 * 查询所有文章
	 * */
	List<JSONObject> selectAll();
}
