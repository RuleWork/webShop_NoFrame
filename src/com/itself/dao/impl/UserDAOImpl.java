package com.itself.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itself.bean.User;
import com.itself.dao.IUserDAO;
import com.itself.util.JDBCUtils;

public class UserDAOImpl implements IUserDAO{

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		qr.update(sql,params);
	}

	@Override
	public User userActive(String code) throws SQLException{
		String sql = "select * from user where code=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		//返回第一个Bean对象 
		User user = qr.query(sql, new BeanHandler<User>(User.class),code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		String sql = "update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params= {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		qr.update(sql,params);
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username=? and password=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		user = qr.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
		return user;
	}

}
