package com.onlineretail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineretail.beans.BillBean;
import com.onlineretail.beans.BillInfoBean;
import com.onlineretail.beans.ProductQtyBean;
import com.onlineretail.dao.entity.Bill;
import com.onlineretail.dao.entity.Product;
import com.onlineretail.dao.repository.BillRepository;
import com.onlineretail.dao.repository.ProductRepository;
import com.onlineretail.exception.CustomException;
import com.onlineretail.util.Category;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;

	/**
	 * This method creates Bill
	 * 
	 * @param bill
	 * @return
	 */
	public Bill createBill(Bill bill) {
		return billRepository.save(bill);
	}

	/**
	 * This method deletes Bill
	 * 
	 * @param id
	 */
	public void deleteBill(Long id) {
		doesBillExists(id);
		billRepository.deleteById(id);
	}

	/**
	 * This method searches all Bills
	 * 
	 * @return
	 */
	public Iterable<Bill> getAllBills() {
		return billRepository.findAll();
	}

	public Bill getBillById(Long billId) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException(
					"Issue with input data: Bill Id  " + billId + " Bill Id does not exists in Bill");
		}
		return optBill.get();
	}

	/**
	 * This method creates or deletes product from Bill
	 * 
	 * @param billUpdateInfo
	 * @param billId
	 * @return
	 */
	public Bill updateBillData(BillInfoBean billUpdateInfo, Long billId) {
		if (null == billUpdateInfo) {
			throw new CustomException("There is no information to be updated for id " + billId);
		}
		Bill bill = doesBillExists(billId);
		if (null != billUpdateInfo.getDeleteProducts()) {
			for (BillBean billBean : billUpdateInfo.getDeleteProducts()) {
				deleteProductFromBIll(billId, billBean.getBarCodeId());
			}
		}
		List<ProductQtyBean> productQtyList = new ArrayList<>();
		if (null != billUpdateInfo.getAddProducts()) {
			for (BillBean billBean : billUpdateInfo.getAddProducts()) {
				ProductQtyBean productQty = initProductQty(billId, billBean.getBarCodeId(), billBean.getQuantity());
				productQtyList.add(productQty);
			}
		}
		calculateBillValues(bill, productQtyList);
		return bill;
	}

	/**
	 * Initialize ProductQty Object
	 * 
	 * @param billId
	 * @param barCodeId
	 * @param quantity
	 * @return
	 */
	private ProductQtyBean initProductQty(Long billId, String barCodeId, int quantity) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException("Issue with input data: Bill Id " + billId + " Bill Id does not exists in Bill");
		}
		Product product = findProductByBarCode(barCodeId);
		ProductQtyBean productQty = new ProductQtyBean(product, quantity);
		return productQty;
	}

	/**
	 * Calculates the Bill Value
	 * 
	 * @param bill
	 * @param productQtyList
	 */
	private void calculateBillValues(Bill bill, List<ProductQtyBean> productQtyList) {
		int productTotal = 0;
		double totalValue = 0;
		double totalCost = 0;

		for (ProductQtyBean productQty : productQtyList) {
			double productValue = calculateProductValue(productQty.getProductQty(),
					productQty.getProduct().getCategory(), productQty.getProduct().getPrice());
			totalValue += productValue;
			totalCost += productQty.getProductQty() * productQty.getProduct().getPrice();
			productTotal++;
		}
		bill.setTotalProduct(productTotal);
		bill.setTotalValue(totalValue);
		bill.setTotalCost(totalCost);
		bill.setTotalTax(totalValue - totalCost);
		billRepository.save(bill);
	}

	/**
	 * Calculate product Value
	 * 
	 * @param quantity
	 * @param category
	 * @param price
	 * @return
	 */
	private double calculateProductValue(long quantity, Category category, double price) {
		double productValue = 0;
		// Default value is for Category C
		productValue = quantity * price;
		if (category.equals(Category.A)) {
			productValue = productValue + (0.1 * productValue);
		} else if (category.equals(Category.B)) {
			productValue = productValue + (0.2 * productValue);
		}
		return productValue;
	}

	/**
	 * 
	 * @param barCodeId
	 * @param productList
	 * @return
	 */
	private Product getProductQtyByBarCode(String barCodeId, List<Product> productList) {
		for (int i = 0; i < productList.size(); i++) {
			Product li = productList.get(i);
			if (barCodeId.equals(li.getBarCodeId())) {
				return li;
			}
		}
		return null;
	}

	/**
	 * This method delete product from Bill
	 * 
	 * @param billId
	 * @param barCodeId
	 * @return
	 */
	private Bill deleteProductFromBIll(Long billId, String barCodeId) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException("Issue with input data: Bill Id  " + billId + " Bill Id does not exists in Bill");
		}
		Bill bill = optBill.get();
		List<Product> productQtyList = bill.getProductList();
		findProductByBarCode(barCodeId);
		if (productQtyList != null && !productQtyList.isEmpty()) {
			Product product = getProductQtyByBarCode(barCodeId, productQtyList);
			if (null == product) {
				throw new CustomException(
						"Issue with input data: Product does not exist. Cannot remove product with BarCode Id "
								+ barCodeId);

			}
			productQtyList.remove(product);
			bill.setProductList(productQtyList);
			billRepository.save(bill);
		} else {
			throw new CustomException(
					"Issue with input data:There are no Product in the Bill. Cannot remove product with BarCode ID "
							+ barCodeId);
		}
		billRepository.save(bill);
		return bill;
	}

	/**
	 * Check if Bill exists in database or not
	 * 
	 * @param billId
	 * @return
	 */
	private Bill doesBillExists(Long billId) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException("Issue with input data: Bill Id  " + billId + " Bill Id does not exists in Bill");
		}
		return optBill.get();
	}

	/**
	 * This method search Product by barCode
	 * 
	 * @param barCodeId
	 * @return
	 */
	private Product findProductByBarCode(String barCodeId) {
		List<Product> productList = productRepository.findProductByBarCodeId(barCodeId);
		if (null == productList || productList.isEmpty()) {
			throw new CustomException("Issue with input data: BarCode Id " + barCodeId + " does not exist in Product");
		}
		return productList.get(0);
	}

}
