package com.lijianan.cms.service;

import java.util.List;

import com.lijianan.cms.domain.Category;

public interface CategoryService {
	/**
	 * 
	 * @Title: selectByChannelId 
	 * @Description: 通过栏目查找对应的分类
	 * @param ChannelId
	 * @return
	 * @return: List<Category>
	 */
	List<Category> selectByChannelId(Integer ChannelId);

}
