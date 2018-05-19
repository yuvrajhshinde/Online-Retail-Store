package com.onlineretail.dao.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BILL")
public class Bill {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long billId;
	private int totalProduct;
	private double totalCost;
	private double totalTax;
	private double totalValue;

	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	private List<Product> productList;

	public Bill() {
		super();
	}

	public Bill(double totalValue, double totalCost, double totalTax, int totalProduct) {
		super();
		this.totalValue = totalValue;
		this.totalCost = totalCost;
		this.totalTax = totalTax;
		this.totalProduct = totalProduct;
	}

	public long getBillId() {
		return billId;
	}

	public void setBillId(long billId) {
		this.billId = billId;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getTotalTax() {
		return totalTax;
	}

	public void setTotalTax(double totalTax) {
		this.totalTax = totalTax;
	}

	public double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(double totalValue) {
		this.totalValue = totalValue;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

}
