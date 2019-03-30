package com.itself.dao;

import java.sql.Connection;
import java.util.List;

import com.itself.bean.Order;
import com.itself.bean.OrderItem;
import com.itself.bean.User;

public interface IOrderDAO {

	public void saveOrder(Connection con, Order order) throws Exception;

	public void saveOrderItem(Connection con, OrderItem item)throws Exception;

	public int getTotalRecords(User user)throws Exception;

	public List findMyOrdersWithPage(User user, int startIndex, int pageSize)throws Exception;

	public Order findOrderByOid(String oid) throws Exception;

	public void updateOrder(Order order) throws Exception;

	public List<Order> findAllOrders() throws Exception;

	public List<Order> findAllOrders(String state) throws Exception;


}
