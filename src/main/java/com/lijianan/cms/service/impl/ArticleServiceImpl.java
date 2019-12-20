package com.lijianan.cms.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijianan.cms.dao.ArticleMapper;
import com.lijianan.cms.domain.Article;
import com.lijianan.cms.domain.ArticleWithBLOBs;
import com.lijianan.cms.domain.Category;
import com.lijianan.cms.domain.Channel;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.service.ArticleService;
import com.lijianan.cms.util.ESUtils;
@Service
public class ArticleServiceImpl implements ArticleService {
	@Resource
	private  ArticleMapper m;
	@Resource
	private RedisTemplate<String, Article> redisTemplate;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	/**
	 * 最新文章和redis整合
	 */
	public PageInfo<Article> selectLast(Article article, Integer page, Integer pageSize) {
		//第一次访问的时候，redis中没有数据，从mysql数据库中获取数据
		//怎么判断是第一次访问？
		//直接查看redis中有没有对应的数据，如果没有，则为第一次访问
		//之后再次访问时，直接从redis中获取数据
		
		//获取List类型的操作对象
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		
		List<Article> articles = null;
		
		if(redisTemplate.hasKey("last_article")) {
			//如果有对应的键，则直接从redis中获取数据
			
			//获取数据
			articles = opsForList.range("last_article", 0, -1);
			/*
			 * if(article.getStatus() != null && article.getStatus() == 1) { //判断当前要审核文章
			 * //审核文章通过以后，要清空redis中对应的数据
			 * 
			 * //清空redis redisTemplate.delete("last_article"); }
			 */
			
		}else {
			//如果没有对应的键
			//从mysql中获取数据
			PageHelper.startPage(page, pageSize);
			articles = m.selects(article);
			
			//获取完数据以后，存入redis中
			opsForList.rightPushAll("last_article", articles);
		}
		
		return new PageInfo<Article>(articles);
	}
	
	/**
	 * 热门文章和redis整合
	 * @param article
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Article> selectHot(Article article, Integer page, Integer pageSize) {
		//第一次访问的时候，redis中没有数据，从mysql数据库中获取数据
		//怎么判断是第一次访问？
		//直接查看redis中有没有对应的数据，如果没有，则为第一次访问
		//之后再次访问时，直接从redis中获取数据
		
		//获取List类型的操作对象
		ListOperations<String, Article> opsForList = redisTemplate.opsForList();
		
		PageInfo<Article> pageInfo = null;
		
		if(redisTemplate.hasKey("hot_article")) {
			//如果有对应的键，则直接从redis中获取数据
			
			//获取数据
			List<Article> articles = opsForList.range("hot_article", (page - 1) * pageSize, page * pageSize - 1);

			//获取总条数
			Long size = opsForList.size("hot_article");
			
			//创建Page对象
			Page<Article> page_list = new Page<Article>(page, pageSize);
			
			//设置数据
			page_list.addAll(articles);
			
			//设置总条数
			page_list.setTotal(size);
			
			pageInfo = new PageInfo<Article>(page_list, 3);
			/*
			 * //审核通过文章 if( article.getHot() != null && article.getHot() == 1) { //判断当前要审核文章
			 * //审核文章通过以后，要清空redis中对应的数据
			 * 
			 * //清空redis redisTemplate.delete("hot_article"); }
			 */
			
			
		}else {
			//如果没有对应的键
			//从mysql中获取所有热门文章的数据
			List<Article> all_articles = m.selects(article);
			
			//获取全部数据以后，存入redis中
			opsForList.rightPushAll("hot_article", all_articles);
			
			//获取分页数据，显示出来
			PageHelper.startPage(page, pageSize);
			//设置数据
			pageInfo = new PageInfo<Article>( m.selects(article));
			
		}
		
		return pageInfo;
	}
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
			int result=m.updateByPrimaryKeySelective(article);
			//审核通过文章
			if(result > 0 && article.getStatus() != null && article.getStatus() == 1) {
				//判断当前要审核文章
				//审核文章通过以后，要清空redis中对应的数据
				
				//清空redis中最新文章的缓存
				redisTemplate.delete("last_article");
				
				//清空redis热门文章的缓存
				redisTemplate.delete("hot_article");
				
				//如果删除文章
				if(article.getDeleted() != null && article.getDeleted() == 1) {
					//删除es中数据
					elasticsearchTemplate.delete(Article.class, article.getId().toString());
					
				}else {
					//存入es中
					IndexQuery query = new IndexQuery();
					//根据子类中id的值，获取到Article对象的数据
					Article art = m.selectByPrimaryKey(article.getId());
					query.setObject(art);
					
					elasticsearchTemplate.index(query);
				}
			}
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
	/**
	 * 高亮查询
	 */
	@Override
	public PageInfo<Article> selectEs(Integer page, Integer pageSize, String key) {
			//实体类中成员变量如果是实体类类型，则将其类对象，存入clazzs中
				Class [] clazzs = new Class[] {User.class,Category.class,Channel.class};
				
				AggregatedPage<Article> selectObjects = ESUtils.selectObjects(elasticsearchTemplate, Article.class, Arrays.asList(clazzs), page-1, pageSize, "id", new String[] {"title"}, key);
				
				List<Article> content = selectObjects.getContent();
				//创建Page对象
				Page<Article> page_list = new Page<Article>(page, pageSize);
				
				//设置数据
				page_list.addAll(content);
				
				//设置总条数
				page_list.setTotal(selectObjects.getTotalElements());
				
				 
				return new PageInfo<Article>(page_list, 3);
	}

}
