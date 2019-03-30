package com.itself.service.impl;

import java.util.List;

import com.itself.bean.Category;
import com.itself.dao.ICategoryDAO;
import com.itself.dao.impl.CategoryDAOImpl;
import com.itself.service.ICategoryService;
import com.itself.util.BeanFactory;
import com.itself.util.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements ICategoryService{

	ICategoryDAO categoryDAO = (ICategoryDAO)BeanFactory.createObject("categoryDAO");
	
	@Override
	public List<Category> getAllCats() throws Exception {
		return categoryDAO.getAllCats();
	}

	@Override
	public void addCategory(Category category) throws Exception {
		//本质是向mysql插入一条数据
		categoryDAO.addCategory(category);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}

}
