package com.itself.service.impl;

import java.util.List;

import com.itself.bean.PageModel;
import com.itself.bean.Product;
import com.itself.dao.IProductDAO;
import com.itself.dao.impl.ProdoctDAOImpl;
import com.itself.service.IProductService;
import com.itself.util.BeanFactory;

public class ProductServiceImpl implements IProductService{

	IProductDAO productDAO = (IProductDAO)BeanFactory.createObject("productDAO");
	
	@Override
	public List<Product> findHots() throws Exception {
		return productDAO.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		return productDAO.findNews();
	}

	@Override
	public Product findByProductByPid(String pid) throws Exception {
		return productDAO.findByProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
		//1.创建一个PageModel对象
		
		//统计当前分类下商品的个数 select count(*) from product where cid=?
		int totalRecords = productDAO.findTotalRecords(cid);
		//当前页，商品总数，根据前端的页面可知一个菜单下放12个商品
		PageModel pm = new PageModel(curNum, totalRecords, 12);
		//2.关联集合 select * from product where cid=? limit ?,?
		List<Product> list = productDAO.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3.关联url
		//ProductServlet?method=findProductByCidWithPage&cid=1
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//创建对象
		int totalRecords = productDAO.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords, 5);
		//关联集合 select * from product limit ?,?
		List<Product> list = productDAO.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		productDAO.saveProduct(product);
	}

}
