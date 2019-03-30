package com.itself.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itself.bean.PageModel;
import com.itself.bean.Product;
import com.itself.service.IProductService;
import com.itself.service.impl.ProductServiceImpl;
import com.itself.util.UploadUtils;
import com.itself.web.base.BaseServlet;


public class ProductServlet extends BaseServlet {
	//findByProductByPid findProductByPid
	public String findProductByPid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取商品pid
		String pid = req.getParameter("pid");
		//根据商品pid查询商品信息
		IProductService prodectService = new ProductServiceImpl();
		Product product = prodectService.findByProductByPid(pid);
		//将获取到的商品放入request
		req.setAttribute("product", product);
		//转发/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}
	
	//findProductsByCidWithPage
	public String findProductsByCidWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取cid,num
		String cid = req.getParameter("cid");
		int curNum = Integer.parseInt(req.getParameter("num"));
		//调用业务层功能，以分页的形式查询当前类别下商品信息
		IProductService productService = new ProductServiceImpl();
		//返回PageModel对象(1_当前页商品信息 2_分页 3_url)
		PageModel pm = productService.findProductsByCidWithPage(cid,curNum);
		//将PageModel对象放入request
		req.setAttribute("page", pm);
		//转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
}
