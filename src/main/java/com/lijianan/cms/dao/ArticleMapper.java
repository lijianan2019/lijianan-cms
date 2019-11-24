package com.lijianan.cms.dao;

import java.util.List;

import com.lijianan.cms.domain.Article;
import com.lijianan.cms.domain.ArticleWithBLOBs;

public interface ArticleMapper {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 文章列表查询
	 * @param article
	 * @return
	 * @return: List<Article>
	 */
	List<Article> selects(Article article);
	
    int deleteByPrimaryKey(Integer id);

    int insert(ArticleWithBLOBs record);

    int insertSelective(ArticleWithBLOBs record);

    ArticleWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ArticleWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ArticleWithBLOBs record);

    int updateByPrimaryKey(Article record);
}