package com.onlineretail.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.onlineretail.beans.BillBean;
import com.onlineretail.beans.BillInfoBean;
import com.onlineretail.dao.entity.Bill;
import com.onlineretail.dao.entity.Product;
import com.onlineretail.dao.repository.BillRepository;
import com.onlineretail.dao.repository.ProductRepository;
import com.onlineretail.service.BillService;

@Component
public class TestDataRunner implements CommandLineRunner {

	@Autowired
	private BillService billService;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private BillRepository billRepository;

	public TestDataRunner() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String... args) throws Exception {
		initProductData();
		initBillData();
		
	/*	Product p = new Product("Test1", "AAAA-5555", 10, Category.A);
		List<Product> pList = new ArrayList();
		pList.add(p);
		Bill b = new Bill(0, 0, 0, 0);
		b.setProductList(pList);
		billRepository.save(b);
		*/

	}

	private void initProductData() {
		productRepository.save(new Product("Test1", "AAAA-0001", 10, Category.A));
		productRepository.save(new Product("Test2", "AAAA-0002", 20, Category.B));
		productRepository.save(new Product("Test3", "AAAA-0003", 30, Category.C));
	}

	private void initBillData() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Long billId = createdBill.getBillId();
		BillInfoBean billInfoBean = new BillInfoBean();
		List<BillBean> productsToBeAdded = new ArrayList<BillBean>();
		List<BillBean> productsToBeRemoved = new ArrayList<BillBean>();
		productsToBeAdded.add(new BillBean("AAAA-0001", 2));
		productsToBeAdded.add(new BillBean("AAAA-0002", 2));
		productsToBeAdded.add(new BillBean("AAAA-0003", 2));
		billInfoBean.setAddProducts(productsToBeAdded);
		billInfoBean.setDeleteProducts(productsToBeRemoved);
		billService.updateBill(billInfoBean, billId);

	}
}
