package com.onlineretail.beans;

import com.onlineretail.dao.entity.Product;

public class ProductQtyBean {

	private long productQty;

	private Product product;
	
	public ProductQtyBean() {
		
	}

	public ProductQtyBean(Product product, int productQty) {
		super();
		this.product = product;
		this.productQty = productQty;
	}

	public long getProductQty() {
		return productQty;
	}

	public void setProductQty(long productQty) {
		this.productQty = productQty;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}