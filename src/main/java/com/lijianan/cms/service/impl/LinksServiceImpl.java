package com.lijianan.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.bwie.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijianan.cms.dao.LinksMapper;
import com.lijianan.cms.domain.Links;
import com.lijianan.cms.service.LinksService;
import com.lijianan.cms.util.CMSAjaxException;

@Service
public class LinksServiceImpl implements LinksService {

	@Resource
	private LinksMapper linksMapper;

	@Override
	public boolean insert(Links links) {
		// 调用工具类判断是否是有效URL
		if (!StringUtils.isHttpUrl(links.getUrl()))
			throw new CMSAjaxException(1, "不是有效的url");
		links.setCreated(new Date());
		linksMapper.insert(links);

		return true;

	}

	@Override
	public PageInfo<Links> selects(Integer page, Integer pageSize) {
		PageHelper.startPage(page, pageSize);
		List<Links> list = linksMapper.selects();
		return new PageInfo<Links>(list);
	}

}
