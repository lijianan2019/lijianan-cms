package com.lijianan.cms.dao;

import java.util.List;

import com.lijianan.cms.domain.Category;

public interface CategoryMapper {
	/**
	 * 
	 * @Title: selectByChannelId 
	 * @Description: 通过栏目查找对应的分类
	 * @param ChannelId
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectByChannelId(Integer ChannelId);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}