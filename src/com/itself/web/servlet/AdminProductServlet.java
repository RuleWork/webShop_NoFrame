package com.itself.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.itself.bean.Category;
import com.itself.bean.PageModel;
import com.itself.bean.Product;
import com.itself.service.ICategoryService;
import com.itself.service.IProductService;
import com.itself.service.impl.CategoryServiceImpl;
import com.itself.service.impl.ProductServiceImpl;
import com.itself.util.UUIDUtils;
import com.itself.util.UploadUtils;
import com.itself.web.base.BaseServlet;


public class AdminProductServlet extends BaseServlet {

	//findAllProductsWithPage
	public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取当前页
		int curNum = Integer.parseInt(req.getParameter("num"));
		//调用业务层查询全部商品信息返回PageModel
		IProductService productService = new ProductServiceImpl();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		//将PageModel放入request
		req.setAttribute("page", pm);
		//转发到/admin/product/list.jsp
		return "/admin/product/list.jsp";
	}
	
	//addProductUI
	public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ICategoryService categoryService = new CategoryServiceImpl();
		//获取全部分类
		List<Category> list = categoryService.getAllCats();
		//将全部分类信息放入request
		req.setAttribute("allCats", list);
		//转发到/admin/product/add.jsp
		return "/admin/product/add.jsp";
	}
		
	//addProduct
		public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
			//存储表单中的数据
			Map<String, String> map = new HashMap<String,String>();
			//携带表单中的数据向service,dao传
			Product product = new Product();
			try {
				//利用req.getInputSteam();获取到请求体中全部数据，进行拆分和封装
				DiskFileItemFactory fac = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(fac);
				List<FileItem> list = upload.parseRequest(req);
				//遍历集合
				for (FileItem item : list) {
					if (item.isFormField()) {
						//如果当前的FileItem对象是普通项
						//将普通项上name属性做为键，将获取到的内容做为值，放入Map中
						//{username==tom,password==1234}
						map.put(item.getFieldName(), item.getString("utf-8"));
					}else {
						//如果当前的FileItem对象是上传项
						
						//获取到要保存文件的名称 
						//获取到原始的文件名
						String oldFileName = item.getName();
						//随机生成一个新的文件名
						String newFileName = UploadUtils.getUUIDName(oldFileName);
						
						//通过FileItem获取到输入流对象，通过输入流可以获取图片二进制数据
						InputStream is = item.getInputStream();
						//获取到当前项目product/3下的真实路径
						String realPath = getServletContext().getRealPath("/products/3");
						String dir = UploadUtils.getDir(newFileName);
						// /f/e/d/c/4/6/6/2
						String path = realPath + dir;
						//内存中声明一个目录
						File newDir = new File(path);
						if (!(newDir.exists())) {
							newDir.mkdirs();
						}
						//在服务器端创建一个空文件(后缀必须和上传到服务器端的文件名一致)
						File finalFile = new File(newDir,newFileName);
						if (!finalFile.exists()) {
							finalFile.createNewFile();
						}
						//建立和空文件对应的输出流
						OutputStream os = new FileOutputStream(finalFile);
						//将输入流中的数据刷到输出流中
						IOUtils.copy(is, os);
						//释放资源
						IOUtils.closeQuietly(is);
						IOUtils.closeQuietly(os);

						//向map中存入一个键值对的数据userhead<==>11.bmp
						map.put("pimage","products/3"+dir+"/"+newFileName);
					}
				}
				//利用BeanUtils将map中的数据填充到Product对象上
				BeanUtils.populate(product, map);
				product.setPid(UUIDUtils.getId());
				product.setPdate(new Date());
				product.setPflag(0);
				//调用service_dao将product上携带的数据存入到数据库，重定向到查询全部商品页面
				IProductService productService = new ProductServiceImpl();
				productService.saveProduct(product);
				
				resp.sendRedirect("/store_v1/AdminProductServlet?method=findAllProductsWithPage&num=1");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
}
