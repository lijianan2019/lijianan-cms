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
	 * @Title: selectLast 
	 * @Description: 最新文章和redis整合
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: PageInfo<Article>
	 */
	PageInfo<Article> selectLast(Article article,Integer page,Integer pageSize);
	/**
	 * 
	 * @Title: selectLast 
	 * @Description: 热门文章和redis整合
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 * @return: PageInfo<Article>
	 */
	PageInfo<Article> selectHot(Article article,Integer page,Integer pageSize);
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
	/**
	 * 
	 * @Title: selectEs 
	 * @Description: 高亮查询
	 * @param page
	 * @param pageSize
	 * @param key
	 * @return
	 * @return: PageInfo<Article>
	 */
	PageInfo<Article> selectEs(Integer page, Integer pageSize, String key);
}
