package com.itself.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itself.bean.Category;
import com.itself.service.ICategoryService;
import com.itself.service.impl.CategoryServiceImpl;
import com.itself.util.UUIDUtils;
import com.itself.web.base.BaseServlet;


public class AdminCategoryServlet extends BaseServlet {
	//findAllCats
	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取全部分类信息
		ICategoryService categoryService = new CategoryServiceImpl();
		List<Category> list= categoryService.getAllCats();
		//放入request
		req.setAttribute("allCats", list);
		//转发到/admin/category/list.jsp
		return "/admin/category/list.jsp";
	}
	
	//addCategoryUI
	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		return "/admin/category/add.jsp";
	}
	
	//addCategory
	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取分类名称
		String cname = req.getParameter("cname");
		//创建分类ID
		String id = UUIDUtils.getId();
		Category category = new Category();
		category.setCid(id);
		category.setCname(cname);
		//调用业务层添加分类功能
		ICategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCategory(category);
		//重定向到查询全部分类信息
		resp.sendRedirect("/store_v1/AdminCategoryServlet?method=findAllCats");
		return null;
	}

}
