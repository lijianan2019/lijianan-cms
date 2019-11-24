package com.lijianan.cms.service;

import com.github.pagehelper.PageInfo;
import com.lijianan.cms.domain.Article;
import com.lijianan.cms.domain.ArticleWithBLOBs;

public interface ArticleService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 文章列表
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: PageInfo<Article>
	 */
	PageInfo<Article> selects(Article article,Integer page,Integer pageSize);
	
	/**
	 * 
	 * @Title: selectByPrimaryKey 
	 * @Description: 查询文章详情
	 * @param id
	 * @return
	 * @return: ArticleWithBLOBs
	 */
	ArticleWithBLOBs selectByPrimaryKey(Integer id);
	
	/**
	 * 
	 * @Title: update 
	 * @Description:修改文章
	 * @param article
	 * @return
	 * @return: boolean
	 */
	boolean update(ArticleWithBLOBs article);
	
	/**
	 * 
	 * @Title: insertSelective 
	 * @Description: 发布文章
	 * @param article
	 * @return
	 * @return: boolean
	 */
	boolean insertSelective(ArticleWithBLOBs article);
}
