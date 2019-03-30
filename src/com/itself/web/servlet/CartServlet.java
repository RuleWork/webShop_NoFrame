	package com.itself.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itself.bean.Cart;
import com.itself.bean.CartItem;
import com.itself.bean.Product;
import com.itself.service.IProductService;
import com.itself.service.impl.ProductServiceImpl;
import com.itself.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	//添加购物项到购物车
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//从session中获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		if (null == cart) {
			//如果获取不到，则创建购物车对象，放在session中	
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		//如果获取到，使用即可
		//获取到商品id，数量
		String pid = req.getParameter("pid");
		int num = Integer.parseInt(req.getParameter("quantity"));
		//通过商品id查询商品对象
		IProductService productService = new ProductServiceImpl();
		Product product = productService.findByProductByPid(pid);
		//获取到待购买的购物项
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		
		//调用购物车上的方法
		cart.addCartItemToCart(cartItem);
		//重定向到(转发会导致数据重复提交问题)
		resp.sendRedirect("/store_v1/jsp/cart.jsp");
		return null;
	}
	
	//removeCartItem
	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取删除商品的pid
		String pid = req.getParameter("id");
		//获取到购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		//调用购物车删除购物项的方法
		cart.removeCartItem(pid);
		//重定向到/jsp/cart.jsp
		resp.sendRedirect("/store_v1/jsp/cart.jsp");
		return null;
	}
	
	//clearCart
	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//获取购物车
		Cart cart = (Cart)req.getSession().getAttribute("cart");
		//调用购物车上清空购物车的方法
		cart.clearCart();
		//重新定向到/jsp/cart.jsp
		resp.sendRedirect("/store_v1/jsp/cart.jsp");
		return null;
	}
}
