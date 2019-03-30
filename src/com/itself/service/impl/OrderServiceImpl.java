package com.itself.service.impl;

import java.sql.Connection;
import java.util.List;

import com.itself.bean.Order;
import com.itself.bean.OrderItem;
import com.itself.bean.PageModel;
import com.itself.bean.User;
import com.itself.dao.IOrderDAO;
import com.itself.dao.impl.OrderDAOImpl;
import com.itself.service.IOrderService;
import com.itself.util.BeanFactory;
import com.itself.util.JDBCUtils;

public class OrderServiceImpl implements IOrderService{
	
	IOrderDAO orderDAO = (IOrderDAO)BeanFactory.createObject("orderDAO");

	@Override
	public void saveOrder(Order order) throws Exception {
		//保存订单和订单下所有的订单项(同时成功同时失败)
		/*try {
			JDBCUtils.startTransaction();
			IOrderDAO orderDAO = new OrderDAOImpl();
			orderDAO.saveOrder(order);
			for (OrderItem item : order.getList()) {
				orderDAO.saveOrderItem(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}*/

		Connection con = null;
		try {
			//获得连接
			con = JDBCUtils.getConnection();
			//开启事务
			con.setAutoCommit(false);
			//保存订单,保证是同一个连接
			orderDAO.saveOrder(con,order);
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDAO.saveOrderItem(con,item);
			}
			//提交
			con.commit();
		} catch (Exception e) {
			//回滚
			con.rollback();
		} /*finally {
			if (null != con) {
				con.close();
				con = null;
			}
		}*/
	
	
	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		//1.创建PageModel对象，目的：计算并且携带分页参数
		//select count(*) from orders where uid=? 
		int totalRecords = orderDAO.getTotalRecords(user);
		PageModel pm = new PageModel(curNum, totalRecords, 3);
		//2.关联集合 select * from orders where uid=? limit ?,?
		List list = orderDAO.findMyOrdersWithPage(user,pm.getStartIndex(),3);
		pm.setList(list);
		//3.关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDAO.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		orderDAO.updateOrder(order);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		return orderDAO.findAllOrders();
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		return orderDAO.findAllOrders(state);
	}

	

}
