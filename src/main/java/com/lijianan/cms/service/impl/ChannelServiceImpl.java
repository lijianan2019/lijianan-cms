package com.lijianan.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lijianan.cms.dao.ChannelMapper;
import com.lijianan.cms.domain.Channel;
import com.lijianan.cms.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {
	@Resource
	private ChannelMapper channelMapper;
	@Override
	public List<Channel> selects() {
		// TODO Auto-generated method stub
		return channelMapper.selects();
	}

}
