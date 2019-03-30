package com.itself.service;

import java.util.List;

import com.itself.bean.PageModel;
import com.itself.bean.Product;

public interface IProductService {

	public List<Product> findHots() throws Exception;

	public List<Product> findNews() throws Exception;

	public Product findByProductByPid(String pid) throws Exception;

	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception;

	public PageModel findAllProductsWithPage(int curNum) throws Exception;

	public void saveProduct(Product product) throws Exception;

}
