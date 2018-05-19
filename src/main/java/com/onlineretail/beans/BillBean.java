package com.onlineretail.beans;

import javax.validation.constraints.NotNull;

public class BillBean {

	@NotNull
	private String barCodeId;

	private int quantity;

	public BillBean(String barCodeId, int quantity) {
		this.barCodeId = barCodeId;
		this.quantity = quantity;
	}

	public String getBarCodeId() {
		return barCodeId;
	}

	public void setBarCodeId(String barCodeId) {
		this.barCodeId = barCodeId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
