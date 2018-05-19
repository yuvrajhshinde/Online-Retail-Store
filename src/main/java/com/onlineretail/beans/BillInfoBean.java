package com.onlineretail.beans;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class BillInfoBean {

	private List<BillBean> addProducts;
	private List<BillBean> deleteProducts;

	public List<BillBean> getAddProducts() {
		return addProducts;
	}

	public void setAddProducts(List<BillBean> addProducts) {
		this.addProducts = addProducts;
	}

	public List<BillBean> getDeleteProducts() {
		return deleteProducts;
	}

	public void setDeleteProducts(List<BillBean> deleteProducts) {
		this.deleteProducts = deleteProducts;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
