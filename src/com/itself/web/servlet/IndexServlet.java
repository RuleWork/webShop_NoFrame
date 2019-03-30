package com.itself.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itself.bean.Product;
import com.itself.service.IProductService;
import com.itself.service.impl.ProductServiceImpl;
import com.itself.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		/*//查询全部分类
		ICategoryService CategoryService=new CategoryServiceImpl();
		List<Category> list=CategoryService.getAllCats();
		//放入request域对象
		req.setAttribute("allCats", list);*/

		IProductService ProductService=new ProductServiceImpl();
		//获取最新9件商品
		List<Product> list01=ProductService.findNews();
		//获取最热9件商品
		List<Product> list02=ProductService.findHots();
		//将最新商品放入request
		req.setAttribute("news", list01);
		//将最热商品放入request
		req.setAttribute("hots", list02);
		
		//转发到/jsp/index.jsp
		return "/jsp/index.jsp";
	}
	
}
