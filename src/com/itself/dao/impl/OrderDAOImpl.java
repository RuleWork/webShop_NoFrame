package com.itself.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itself.bean.Order;
import com.itself.bean.OrderItem;
import com.itself.bean.Product;
import com.itself.bean.User;
import com.itself.dao.IOrderDAO;
import com.itself.util.JDBCUtils;
import com.itself.util.MyBeanUtils;

public class OrderDAOImpl implements IOrderDAO{

	@Override
	public void saveOrder(Connection con, Order order) throws Exception {
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = {order.getOid(),order.getOrderTime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		qr.update(con,sql,params);
	}

	@Override
	public void saveOrderItem(Connection con, OrderItem item) throws Exception {
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		qr.update(con,sql,params);
	}

	@Override
	public int getTotalRecords(User user) throws Exception {
		String sql = "select count(*) from orders where uid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		//返回查询结果的第一列的数据
		Long num = (Long)qr.query(sql, new ScalarHandler(),user.getUid());
		return num.intValue();
	}

	@Override
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "select * from orders where uid=? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = qr.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
		
		//遍历所有订单
		for (Order order : list) {
			//获取到每笔订单的id 查询每笔订单下的订单项目以及订单项对应的商品信息
			String oid = order.getOid();
			sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
			List<Map<String,Object>> list02 = qr.query(sql, new MapListHandler(),oid);
			//遍历list
			for (Map<String,Object> map: list02) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				//将map中属于orderitem的数据自动填充到orderitem中
				MyBeanUtils.populate2(orderItem, map);
				//将map中属于product的数据自动填充到product中
				MyBeanUtils.populate2(product, map);
				
				//让每个订单项和商品关联关系
				orderItem.setProduct(product);
				//将每个订单项存入订单下的集合
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		//根据订单的id查询订单下所有订单项以及订单项对应的商品信息
		sql = "select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
		List<Map<String,Object>> list02 = qr.query(sql, new MapListHandler(),oid);
		//遍历list
		for (Map<String,Object> map: list02) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			//将map中属于orderitem的数据自动填充到orderitem中
			MyBeanUtils.populate2(orderItem, map);
			//将map中属于product的数据自动填充到product中
			MyBeanUtils.populate2(product, map);
			
			//让每个订单项和商品关联关系
			orderItem.setProduct(product);
			//将每个订单项存入订单下的集合
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		String sql = "update orders set ordertime=?,total=?,state=?,address=?,name=?,telephone=? where oid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params={order.getOrderTime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		qr.update(sql,params);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		String sql = "select * from orders";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		String sql = "select * from orders where state=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class),state);
	}
	
}
