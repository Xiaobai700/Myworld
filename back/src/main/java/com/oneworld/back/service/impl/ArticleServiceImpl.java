package com.oneworld.back.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.oneworld.back.dao.ArticleDao;
import com.oneworld.back.service.ArticleService;
import com.oneworld.back.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;

	/**
	 * 新增文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject addArticle(JSONObject jsonObject) {
		articleDao.addArticle(jsonObject);
		return CommonUtil.successJson();
	}

	/**
	 * 文章列表
	 */
	@Override
	public JSONObject listArticle(JSONObject jsonObject) {
		CommonUtil.fillPageParam(jsonObject);
		int count = articleDao.countArticle(jsonObject);
		List<JSONObject> list = articleDao.listArticle(jsonObject);
		return CommonUtil.successPage(jsonObject, list, count);
	}

	/**
	 * 更新文章
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateArticle(JSONObject jsonObject) {
		articleDao.updateArticle(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public JSONObject deleteArticle(JSONObject jsonObject) {
		articleDao.deleteArticle(jsonObject);
		return CommonUtil.successJson();
	}

	@Override
	public List<JSONObject> selectAll() {
		return articleDao.selectAll();
	}

	@Override
	public JSONObject deleteArticles(Long[] deleteArr) {
		articleDao.deleteArticles(deleteArr);
		return CommonUtil.successJson();
	}
}
