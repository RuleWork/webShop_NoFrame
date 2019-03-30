package com.itself.dao;

import java.util.List;

import com.itself.bean.Category;

public interface ICategoryDAO {

	public List<Category> getAllCats() throws Exception;

	public void addCategory(Category category) throws Exception;


}
