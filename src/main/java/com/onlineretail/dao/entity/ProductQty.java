package com.onlineretail.dao.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "PRODUCTQTY")
public class ProductQty {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	private Product product;

	private long productQty;

	public ProductQty() {
		super();
	}

	public ProductQty(Product product, int productQty) {
		super();
		this.product = product;
		this.productQty = productQty;
	}

	public long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getProductQty() {
		return productQty;
	}

	public void setProductQty(long productQty) {
		this.productQty = productQty;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
