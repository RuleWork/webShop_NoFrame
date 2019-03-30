package com.itself.service;

import java.util.List;

import com.itself.bean.Category;

public interface ICategoryService {

	public List<Category> getAllCats() throws Exception;

	public void addCategory(Category category) throws Exception;

}
