package com.lijianan.cms.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.lijianan.cms.domain.User;
import com.lijianan.cms.vo.UserVO;

public interface UserService {
	/**
	 * 
	 * @Title: selects 
	 * @Description: 用户列表
	 * @param user
	 * @return
	 * @return: List<User>
	 */
	PageInfo<User> selects(User user,Integer page,Integer pageSize);

	boolean update(User user);
	/**
	 * 
	 * @Title: login 
	 * @Description:用户登录
	 * @param user
	 * @return
	 * @return: User
	 */
	User login(User user);
	/**
	 * 
	 * @Title: insertSelective 
	 * @Description: 用户注册
	 * @param userVO
	 * @return
	 * @return: boolean
	 */
	boolean insertSelective(UserVO userVO);
}
