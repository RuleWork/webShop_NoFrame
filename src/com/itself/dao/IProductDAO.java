package com.itself.dao;

import java.util.List;

import com.itself.bean.Product;

public interface IProductDAO {

	public List<Product> findHots() throws Exception;

	public List<Product> findNews() throws Exception;

	public Product findByProductByPid(String pid) throws Exception;

	public int findTotalRecords(String cid) throws Exception;
	
	public List<Product> findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception;

	public int findTotalRecords() throws Exception;

	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception;

	public void saveProduct(Product product)throws Exception;

}
