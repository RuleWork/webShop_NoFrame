package com.itself.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itself.bean.Category;
import com.itself.dao.ICategoryDAO;
import com.itself.service.ICategoryService;
import com.itself.util.JDBCUtils;

public class CategoryDAOImpl implements ICategoryDAO {

	@Override
	public List<Category> getAllCats() throws Exception {
		String sql = "select * from category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category category) throws Exception {
		String sql = "insert into category values (?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,category.getCid(),category.getCname());
	}

	

}
