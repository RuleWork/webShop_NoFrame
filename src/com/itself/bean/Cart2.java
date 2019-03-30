package com.itself.bean;

import java.util.ArrayList;
import java.util.List;

//2个属性 3个方法
public class Cart2 {
	//个数不确定的购物项
	private List<CartItem> list = new ArrayList<CartItem>();
	//总计/积分
	private double total;

	//添加购物项到购物车
	//当用户点击加入购物车按钮，可以将当前要购买的商品id,商品数量发送到服务端，服务端根据
	//商品id查询到商品信息，有了商品信息Product对象，有了要购买商品数量，当前购物项也就获取到了
	public void addCartItemToCart(CartItem cartItem_new) {
		//将当前购物项加入购物车之前，判断之前是否买过这类商品
		//如果没有买过list.add();
		
		//设置变量，默认为false，没有购买过商品
		boolean flag = false;
		//用来存储原先的购物项
		CartItem old = null;
		for (CartItem cartItem_old : list) {
			if (cartItem_old .getProduct().getPid().equals(cartItem_new.getProduct().getPid())) {
				flag = true;
				old = cartItem_old;
			}
		}
		
		if (flag) {
			list.add(cartItem_new);
		}else {
			//如果买过：获取原先的数量，获取本次的数量，将数量相加之后设置到原先的购物项上
			old.setNum(old.getNum()+cartItem_new.getNum());
		}
	}
	
	//移除购物项
	//当用户点击移除购物项(删除)这个链接时，可以将当前的购物类别的商品id发送到服务端
	public void removeCartItem(String pid) {
		//遍历List，看每个CartItem上的Product对象上的id是否和服务端获取到的pid一致，如果相等则删除
		for (CartItem cartItem : list) {
			if (cartItem.getProduct().getPid().equals(pid)) {
				//删除当前的cartItem
				//直接调用list.remove(cartItem);是无法删除当前的cartItem的，因为传入的参数时int
				//需要通过迭代器来删除
				list.remove(cartItem);
			}
		}
	}
	//清空购物车
	public void clearCart() {
		list.clear();
	}
}
