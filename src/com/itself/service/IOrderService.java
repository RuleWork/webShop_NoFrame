package com.itself.service;

import java.util.List;

import com.itself.bean.Order;
import com.itself.bean.PageModel;
import com.itself.bean.User;

public interface IOrderService {
	public void saveOrder(Order order) throws Exception;

	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	public Order findOrderByOid(String oid) throws Exception;

	public void updateOrder(Order order) throws Exception;

	public List<Order> findAllOrders() throws Exception;

	public List<Order> findAllOrders(String state) throws Exception;


}
