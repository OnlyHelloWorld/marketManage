package com.star.service.impl;

import com.star.dao.IUserDAO;
import com.star.domain.User;
import com.star.service.IUserService;
import com.star.utils.MD5Utils;

public class UserServiceImpl implements IUserService{

	
	private IUserDAO userDAO;

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public User getUserByCodePassword(User u) {
		//根据用户名查询登录用户
		User existUser = userDAO.getByUserCode(u.getUser_code());
		//判断用户是否存在,不存在,抛出异常,提示用户
		if (existUser == null) {
			throw new RuntimeException("用户名不存在");
		}
		//判断密码,不正确,抛出异常,提示用户
		if (!existUser.getUser_password().equals(MD5Utils.md5(u.getUser_password()))) {
			throw new RuntimeException("用户名或者密码错误");
		}
		//返回查询到的用户对象

		return existUser;
	}

	@Override
	public void saveUser(User u) {
		//获取用户
		User user = userDAO.getByUserCode(u.getUser_code());
		//user!=null 异常
		if (user != null) {
			throw new RuntimeException("用户名已经被注册!");
		}
		u.setUser_password(MD5Utils.md5(u.getUser_password()));
		//User== null 保存
		userDAO.save(u);
		
	}


}
