package com.onlineretail.beans;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.onlineretail.util.Category;

public class ProductBean {

	@NotNull
	private String name;

	@NotNull
	private String barCodeId;

	@NotNull
	@DecimalMin(value = "1")
	private long price;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Category category;

	public ProductBean() {
	}

	public ProductBean(String name, String barCodeId, long price, Category category) {
		this.name = name;
		this.barCodeId = barCodeId;
		this.price = price;
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBarCodeId() {
		return barCodeId;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
