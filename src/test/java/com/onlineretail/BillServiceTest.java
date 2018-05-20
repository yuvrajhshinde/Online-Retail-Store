package com.onlineretail;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineretail.beans.BillBean;
import com.onlineretail.beans.BillInfoBean;
import com.onlineretail.beans.ProductBean;
import com.onlineretail.dao.entity.Bill;
import com.onlineretail.service.BillService;
import com.onlineretail.service.ProductService;
import com.onlineretail.util.Category;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BillServiceTest {

	@Autowired
	private BillService billService;

	@Autowired
	private ProductService productService;

	@Test
	public void testCreateBill() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Bill bill = billService.getBillById(createdBill.getBillId());
		assertThat(bill.getBillId()).isEqualTo(createdBill.getBillId());
	}

	@Test
	public void testBillWithMultipleProducts() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Long billId = createdBill.getBillId();
		
		productService.createProduct(new ProductBean("Test1", "AAAA-4444", 20, Category.A));
		productService.createProduct(new ProductBean("Test2", "BBBB-4444", 20, Category.B));
		productService.createProduct(new ProductBean("Test3", "CCCC-4444", 20, Category.C));
		
		
		BillInfoBean billInfoBean = new BillInfoBean();
		List<BillBean> productsToBeAdded = new ArrayList<BillBean>();
		List<BillBean> productsToBeRemoved = new ArrayList<BillBean>();
		productsToBeAdded.add(new BillBean("AAAA-4444", 2));
		productsToBeAdded.add(new BillBean("BBBB-4444", 2));
		productsToBeAdded.add(new BillBean("CCCC-4444", 2));
		
		billInfoBean.setAddProducts(productsToBeAdded);
		billInfoBean.setDeleteProducts(productsToBeRemoved);
		billService.updateBill(billInfoBean, billId);
		Bill retrieveUpdatedBill = billService.getBillById(createdBill.getBillId());
		assertThat(retrieveUpdatedBill.getTotalProduct()).isEqualTo(3);
	}

	@Test
	public void testCreateBillwithProductCategoryA() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Long billId = createdBill.getBillId();
		productService.createProduct(new ProductBean("ACatergoryProduct", "AAAA-1111", 20, Category.A));
		BillInfoBean billInfoBean = new BillInfoBean();
		List<BillBean> productsToBeAdded = new ArrayList<BillBean>();
		productsToBeAdded.add(new BillBean("AAAA-1111", 2));
		billInfoBean.setAddProducts(productsToBeAdded);
		billService.updateBill(billInfoBean, billId);
		Bill updatedBill = billService.getBillById(billId);
		assertThat(updatedBill.getTotalValue() == 44);
	}

	@Test
	public void testCreateBillwithProductCategoryB() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Long billId = createdBill.getBillId();
		productService.createProduct(new ProductBean("BCatergoryProduct", "BBBB-2222", 20, Category.A));
		BillInfoBean billInfoBean = new BillInfoBean();
		List<BillBean> productsToBeAdded = new ArrayList<BillBean>();
		productsToBeAdded.add(new BillBean("BBBB-2222", 2));
		billInfoBean.setAddProducts(productsToBeAdded);
		billService.updateBill(billInfoBean, billId);
		Bill updatedBill = billService.getBillById(billId);
		assertThat(updatedBill.getTotalValue() == 48);
	}

	@Test
	public void testCreateBillwithProductCategoryC() {
		Bill createdBill = billService.createBill(new Bill(0, 0, 0, 0));
		Long billId = createdBill.getBillId();
		productService.createProduct(new ProductBean("BCatergoryProduct", "CCCC-3333", 20, Category.A));
		BillInfoBean billInfoBean = new BillInfoBean();
		List<BillBean> productsToBeAdded = new ArrayList<BillBean>();
		productsToBeAdded.add(new BillBean("CCCC-3333", 2));
		billInfoBean.setAddProducts(productsToBeAdded);
		billService.updateBill(billInfoBean, billId);
		Bill updatedBill = billService.getBillById(billId);
		assertThat(updatedBill.getTotalValue() == 40);
	}

}
