package com.itself.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


//2个属性 3个方法
public class Cart {
	//总计/积分
	private double total=0;
	//个数不确定的购物项 商品pid<==>CartItem
	Map<String, CartItem> map = new HashMap<String,CartItem>();
	
	//添加购物项到购物车
	public void addCartItemToCart(CartItem cartItem_new) {
		
		//获取新加入的商品的pid
		String pid = cartItem_new.getProduct().getPid();
		if (map.containsKey(pid)) {
			//获取原先的购物项
		  	CartItem oldItem = map.get(pid);
		  	oldItem.setNum(oldItem.getNum()+cartItem_new.getNum());
		}else {
			map.put(pid, cartItem_new);
		}
	}
	//返回map中所有的值
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
	
	//移除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	//清空购物车
	public void clearCart() {
		map.clear();
	}
	//计算总计
	public double getTotal() {
		//先让总计清0
		total = 0;
		//获取到map中所有的购物项
		Collection<CartItem> values = map.values();
		//遍历所有的购物项，将购物项上的小计相加
		for (CartItem cartItem : values) {
			total+=cartItem.getSubTotal();
		}
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	
}
