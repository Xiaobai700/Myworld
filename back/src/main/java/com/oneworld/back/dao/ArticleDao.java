package com.oneworld.back.dao;

import com.alibaba.fastjson.JSONObject;

import java.util.List;


public interface ArticleDao {
	/**
	 * 新增文章
	 */
	int addArticle(JSONObject jsonObject);

	/**
	 * 统计文章总数
	 */
	int countArticle(JSONObject jsonObject);

	/**
	 * 文章列表
	 */
	List<JSONObject> listArticle(JSONObject jsonObject);

	/**
	 * 更新文章
	 */
	int updateArticle(JSONObject jsonObject);

	/**
	 * 删除文章
	 * */
	int deleteArticle(JSONObject jsonObject);

	/**
	 * 查询所有文章
	 * */
	List<JSONObject> selectAll();

	/**
	 * 批量删除文章
	 * */
	int deleteArticles(Long[] deleteArr);
}
