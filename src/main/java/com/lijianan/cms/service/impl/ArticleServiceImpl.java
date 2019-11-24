package com.lijianan.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijianan.cms.dao.ArticleMapper;
import com.lijianan.cms.domain.Article;
import com.lijianan.cms.domain.ArticleWithBLOBs;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.service.ArticleService;
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private  ArticleMapper m;
	@Override
	public PageInfo<Article> selects(Article article, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		List<Article> articles = m.selects(article);
		return new PageInfo<Article>(articles);
		
	}
	@Override
	public ArticleWithBLOBs selectByPrimaryKey(Integer id) {
		// TODO Auto-generated method stub
		return m.selectByPrimaryKey(id);
	}
	@Override
	public boolean update(ArticleWithBLOBs article) {
		// TODO Auto-generated method stub
		try {
			m.updateByPrimaryKeySelective(article);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("修改是否热门失败咯！");
		}
	}
	@Override
	public boolean insertSelective(ArticleWithBLOBs record) {
		try {
			return m.insertSelective(record)>0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new RuntimeException("发布文章添加到文章表出了问题咯！");
		}
		
		
	}

}
