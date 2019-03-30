package com.itself.service.impl;

import java.sql.SQLException;

import com.itself.bean.User;
import com.itself.dao.IUserDAO;
import com.itself.dao.impl.UserDAOImpl;
import com.itself.service.IUserService;
import com.itself.util.BeanFactory;

public class UserServiceImpl implements IUserService{

	IUserDAO userDAO = (IUserDAO)BeanFactory.createObject("userDAO");
	
	@Override
	public void userRegist(User user) throws SQLException {
		//实现注册功能
		userDAO.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		//实现激活功能
		//对DB发送一个select * from user where code=?
		User user = userDAO.userActive(code);
		
		if (user != null) {
			//可以根据一个激活码查询到一个用户
			//修改用户的状态，并清除激活码
			user.setState(1);
			user.setCode(null);
			//对数据库执行一次真实的更新操作
			userDAO.updateUser(user); 
			return true;
		}else {
			//不可以根据一个激活码查询到一个用户
			return false;
		}
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		//可以利用异常在模块之间去传递数据
		
		//select * from user where username=? and password=?
		User u = userDAO.userLogin(user);
		if (u == null) {
			//但凡能走到这就是密码不对，因为ajax可以判断用户名存在与否
			throw new RuntimeException("密码不正确！");
		}else if(u.getState() == 0) {
			throw new RuntimeException("用户没有激活！");
		}else {
			return u;
		}
	}

}
