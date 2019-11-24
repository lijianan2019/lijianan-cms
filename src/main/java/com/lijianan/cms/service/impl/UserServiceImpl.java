package com.lijianan.cms.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import com.bwie.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lijianan.cms.dao.UserMapper;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.service.UserService;
import com.lijianan.cms.util.CMSAjaxException;
import com.lijianan.cms.util.CMSException;
import com.lijianan.cms.util.Md5Util;
import com.lijianan.cms.vo.UserVO;
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper userMapper;
	@Override
	public PageInfo<User> selects(User user, Integer page, Integer pageSize) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, pageSize);
		List<User> users = userMapper.selects(user);
		return new PageInfo<User>(users);
	}
	@Override
	public boolean update(User user) {
		// TODO Auto-generated method stub
		try {
			userMapper.updateByPrimaryKeySelective(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CMSAjaxException(1,"修改用户状态操作失败");
			
		}
		
	}
	@Override
	public boolean insertSelective(UserVO userVO) {
		
			//判断注册信息是否满足要求
			if(!StringUtils.hasText(userVO.getUsername()))
			 throw new CMSException("用户名不能为空");	
			if(!StringUtils.hasText(userVO.getPassword()))
			 throw new CMSException("密码不能为空");	
			if(!StringUtils.hasText(userVO.getRepassword()))
				 throw new CMSException("确认密码不能为空");
			if(!userVO.getPassword().equals(userVO.getRepassword()))
				 throw new CMSException("两次密码不一致");
			//用户名不能重复注册
			User u = userMapper.selectByName(userVO.getUsername());
			if(null!=u)
				 throw new CMSException("用户名不能重复注册");
			//执行注册
			//对密码进行加密保存
			userVO.setPassword(Md5Util.md5Encoding(userVO.getPassword()));
			
			//用户注册的其他属性默认值
			userVO.setCreated(new Date());//注册时间
			userVO.setNickname(userVO.getUsername());//昵称
			
			return userMapper.insertSelective(userVO)>0;
		
	}

	@Override
	public User login(User user) {
		//判断登录注册信息是否满足要求
		if(!StringUtils.hasText(user.getUsername()))
		 throw new CMSException("用户名不能为空");	
		if(!StringUtils.hasText(user.getPassword()))
		 throw new CMSException("密码不能为空");	
		//查询用户名是否存在
		User u = userMapper.selectByName(user.getUsername());
		if(null==u)
			 throw new CMSException("用户名不存在");
		else if(u.getLocked()==1) {
			throw new CMSException("账户被禁用!");
		}else if(!Md5Util.md5Encoding(user.getPassword()).equals(u.getPassword())) {
				//对密码进行加密比较
				throw new CMSException("密码错误!");	
		}
		
		return u;
	}

}
