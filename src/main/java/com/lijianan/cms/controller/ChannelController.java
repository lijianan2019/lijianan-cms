package com.lijianan.cms.controller;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lijianan.cms.domain.Channel;
import com.lijianan.cms.service.ChannelService;
import com.lijianan.cms.util.Result;
import com.lijianan.cms.util.ResultUtil;
/**
 * 
 * @ClassName: ChannelController 
 * @Description: 栏目
 * @author: 李嘉楠
 * @date: 2019年11月14日 下午8:03:48
 */
@RequestMapping("channel")
@Controller
public class ChannelController {
	@Resource
	private ChannelService channelService;
	/**
	 * 
	 * @Title: channels 
	 * @Description: 查询所有栏目
	 * @return
	 * @return: List<Channel>
	 */
	@ResponseBody
	@RequestMapping("channels")
	public  Result<Channel> channels(){
		return ResultUtil.success(channelService.selects());
	}
}
