package com.star.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.star.domain.User;
import com.star.service.IUserService;


public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user = new User();
	
	private IUserService userService ;
	

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public String login() throws Exception {
			//1 调用Service执行登陆逻辑
			User u = userService.getUserByCodePassword(user);
			//2 将返回的User对象放入session域
			ActionContext.getContext().getSession().put("user", u);
			//3 重定向到项目首页
		return "toHome";
	}
	public String regist() throws Exception {
		try {
			userService.saveUser(user);
		} catch (Exception e) {
			e.printStackTrace();
			ActionContext.getContext().put("error", e.getMessage());
			return "regist";
		}
		return "toLogin";
}

	@Override
	public User getModel() {
		return user;
	}

}

