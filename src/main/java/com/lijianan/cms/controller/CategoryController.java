package com.lijianan.cms.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lijianan.cms.domain.Category;
import com.lijianan.cms.service.CategoryService;
import com.lijianan.cms.util.Result;
import com.lijianan.cms.util.ResultUtil;
@RequestMapping("category")
@Controller
public class CategoryController {
	@Resource
	private CategoryService categoryService;
	/**
	 * 
	 * @Title: selects 
	 * @Description:根据栏目查询分类
	 * @param channelId
	 * @return
	 * @return: List<Category>
	 */
	@ResponseBody
	@RequestMapping("selects")
	public Result<Category> selects(Integer channelId){
		return ResultUtil.success(categoryService.selectByChannelId(channelId));
		
	}

}
