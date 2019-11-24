package com.lijianan.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lijianan.cms.dao.CategoryMapper;
import com.lijianan.cms.domain.Category;
import com.lijianan.cms.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Resource
	private CategoryMapper categoryMapper;
	@Override
	public List<Category> selectByChannelId(Integer ChannelId) {
		// TODO Auto-generated method stub
		return categoryMapper.selectByChannelId(ChannelId);
	}

}
