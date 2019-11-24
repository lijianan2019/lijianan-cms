package com.lijianan.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bwie.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijianan.cms.dao.CollectMapper;
import com.lijianan.cms.domain.Collect;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.service.CollectService;
import com.lijianan.cms.util.CMSAjaxException;


@Service
public class CollectServiceImpl implements CollectService {
	@Resource
	private CollectMapper collectMapper;

	@Override
	public boolean insert(Collect collect) {
		if(!StringUtils.isHttpUrl(collect.getUrl()))
			throw new CMSAjaxException(1, "不是有效的URL");
		collect.setCreated(new Date());
		collectMapper.insert(collect);
		return true;
	}

	@Override
	public PageInfo<Collect> selects(Integer page,Integer pageSize ,User user) {
		PageHelper.startPage(page, pageSize);
		List<Collect> list = collectMapper.selects( user);
		return new PageInfo<Collect>(list);
	}

	@Override
	public boolean deleteById(Integer id) {
		collectMapper.deleteById(id);
		return true;
	}

	@Override
	public int selectByText(String text, User user) {
		return collectMapper.selectByText(text, user);
	}

}
