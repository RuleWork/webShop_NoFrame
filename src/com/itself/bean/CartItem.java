package com.itself.bean;

public class CartItem {
	private Product product;//目的是携带购物项的3种参数：图片路径，商品名称，商品价格
	private int num;//当前类别商品数量
	private double subTotal ;//小计
	
	//小计=价格*数量	
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
