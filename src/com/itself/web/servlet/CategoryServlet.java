package com.itself.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itself.bean.Category;
import com.itself.service.ICategoryService;
import com.itself.service.impl.CategoryServiceImpl;
import com.itself.util.JedisUtils;
import com.itself.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;


public class CategoryServlet extends BaseServlet {

	//findAllCats
	public String findAllCates(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//在redis中获取全部分类
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if (jsonStr==null || "".equals(jsonStr)) {
			//调用业务层获取全部分类
			ICategoryService categoryService = new CategoryServiceImpl();
			List<Category> catsList = categoryService.getAllCats();
			//将全部分类转换为json格式的数据
			jsonStr = JSONArray.fromObject(catsList).toString();
			//将获取到的json格式的数据存入redis
			jedis.set("allCats", jsonStr);
			
			System.out.println("缓存中没有数据");
		}else {
			System.out.println("缓存中有数据");
		}
		//将全部分类信息响应到客户端
		//告诉浏览器本次响应的是json格式的字符串
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().print(jsonStr);
		JedisUtils.closeJedis(jedis);
		return null;
	}
}
 