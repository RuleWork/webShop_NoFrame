package com.itself.dao.impl;
/**
 *  AbstractListHandler -- 返回多行List的抽象类
	ArrayHandler --  返回一行的Object[]
	ArrayListHandler -- 返回List，每行是Object[]
	BeanHandler -- 返回第一个Bean对象
	BeanListHandler -- 返回List，每行是Bean
	ColumnListHandler -- 返回一列的List
	KeyedHandler -- 返回Map，具体见代码
	MapHandler -- 返回单个Map
	MapListHandler -- 返回List，每行是Map
	ScalarHandler -- 返回列的头一个值
 */
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itself.bean.Product;
import com.itself.dao.IProductDAO;
import com.itself.util.JDBCUtils;

public class ProdoctDAOImpl implements IProductDAO {

	@Override
	public List<Product> findHots() throws Exception {
		String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0 ,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() throws Exception {
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0 ,9";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product findByProductByPid(String pid) throws Exception {
		String sql = "select * from product where pid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanHandler<Product>(Product.class),pid);
	}

	@Override
	public int findTotalRecords(String cid) throws Exception {
		String sql = "select count(*) from product where cid=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)qr.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

	@Override
	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception {
		String sql = "select * from product where cid =? limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
	}

	@Override
	public int findTotalRecords() throws Exception {
		String sql = "select count(*) from product";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)qr.query(sql, new ScalarHandler());
		return num.intValue();
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
		String sql = "select * from product order by pdate desc limit ?,?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		qr.update(sql,params);
	}
}
