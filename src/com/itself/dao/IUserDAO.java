package com.itself.dao;

import java.sql.SQLException;

import com.itself.bean.User;

public interface IUserDAO {

	public void userRegist(User user) throws SQLException;

	public User userActive(String code) throws SQLException;

	public void updateUser(User user) throws SQLException;

	public User userLogin(User user) throws SQLException;

}
