package com.itself.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itself.bean.Order;
import com.itself.service.IOrderService;
import com.itself.service.impl.OrderServiceImpl;
import com.itself.web.base.BaseServlet;

import net.sf.json.JSONArray;


public class AdminOrderServlet extends BaseServlet {
	//findOrders&num=1
	public String findOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		IOrderService orderService = new OrderServiceImpl();
		
		String state = req.getParameter("state");
		List<Order> list=null;
		if(null==state||"".equals(state)){
			//获取到全部订单
			list=orderService.findAllOrders();			
		}else{
			list=orderService.findAllOrders(state);
		}
		//将全部订单放入request
		req.setAttribute("allOrders", list);
		//转发 /admin/order/list.jsp
		return "/admin/order/list.jsp";
	}
	
	//findOrderByOidWithAjax
	public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//服务端获取订单id
		String oid = req.getParameter("id");
		//查询这个订单下所有的订单项以及订单项对应的商品信息，返回集合
		IOrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		//将返回的集合转换为Json字符串
		String jsonStr = JSONArray.fromObject(order.getList()).toString();   
		//响应到客户端
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().println(jsonStr);
		return null;
	}
	
	//updateOrderByOid
	public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取订单id
		String oid = req.getParameter("oid");
		//根据订单id查询订单
		IOrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		//设置订单状态
		order.setState(3);
		//修改订单信息
		orderService.updateOrder(order);
		//重定向到查询已发货订单
		resp.sendRedirect("/store_v1/AdminOrderServlet?method=findOrders&state=3");
		return null;
	}	
}
