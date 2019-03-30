package com.itself.service;

import java.sql.SQLException;

import com.itself.bean.User;

public interface IUserService {

	public void userRegist(User user) throws SQLException;

	public boolean userActive(String code) throws SQLException;

	public User userLogin(User user) throws SQLException;

}
 