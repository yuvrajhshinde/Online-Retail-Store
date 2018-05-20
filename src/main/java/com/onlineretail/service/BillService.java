package com.onlineretail.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineretail.beans.BillBean;
import com.onlineretail.beans.BillInfoBean;
import com.onlineretail.dao.entity.Bill;
import com.onlineretail.dao.entity.Product;
import com.onlineretail.dao.entity.ProductQty;
import com.onlineretail.dao.repository.BillRepository;
import com.onlineretail.dao.repository.ProductQtyRepository;
import com.onlineretail.dao.repository.ProductRepository;
import com.onlineretail.exception.CustomException;
import com.onlineretail.util.Category;

@Service
public class BillService {

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductQtyRepository productQtyRepository;

	public Bill createBill(Bill bill) {
		return billRepository.save(bill);
	}

	public void deleteBill(Long id) {
		checkBillById(id);
		billRepository.deleteById(id);
	}

	public Iterable<Bill> getAllBills() {
		return billRepository.findAll();
	}

	public Bill getBillById(Long billId) {
		checkBillById(billId);
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException(
					"Problem with input data: Bill ID  " + billId + " Bill ID does not exists in Billt");
		}

		return optBill.get();
	}

	private Bill addProductToBill(Long billId, String barCodeId, int quantity) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException(
					"Problem with input data: Bill Id " + billId + " Bill Id does not exists in Bill");
		}

		Bill bill = optBill.get();
		Product product = findProductByBarCode(barCodeId);
		ProductQty productQty = new ProductQty(product, quantity);
		productQtyRepository.save(productQty);
		List<ProductQty> productQtyList = bill.getProductQtyList();
		if (productQtyList != null) {
			ProductQty existingList = getProductQtyByBarCode(barCodeId, productQtyList);
			if (existingList == null) {
				bill.getProductQtyList().add(productQty);
			} else {
				long newQty = existingList.getProductQty() + quantity;
				existingList.setProductQty(newQty);
			}

		} else {
			productQtyList = new ArrayList<>();
			productQtyList.add(productQty);
			bill.setProductQtyList(productQtyList);
		}
		billRepository.save(bill);
		return bill;
	}

	private void calculateTotalValues(Bill bill) {
		int productTotal = 0;
		double totalValue = 0;
		double totalCost = 0;
		if (null != bill.getProductQtyList()) {
			for (ProductQty productQty : bill.getProductQtyList()) {
				double productValue = calculateProductValue(productQty.getProductQty(),
						productQty.getProduct().getCategory(), productQty.getProduct().getPrice());
				totalValue += productValue;
				totalCost += productQty.getProductQty() * productQty.getProduct().getPrice();
				productTotal++;
			}
		}
		bill.setTotalProduct(productTotal);
		bill.setTotalValue(totalValue);
		bill.setTotalCost(totalCost);
		bill.setTotalTax(totalValue - totalCost);
		billRepository.save(bill);
	}

	private double calculateProductValue(long quantity, Category category, double price) {
		double productValue = 0;
	
		if (category.equals(Category.A)) {
			productValue = quantity * price;
			productValue = productValue + (0.1 * productValue);
		} else if (category.equals(Category.B)) {
			productValue = quantity * price;
			productValue = productValue + (0.2 * productValue);
		}else if (category.equals(Category.C)) {
			productValue = quantity * price;;
		}
		return productValue;
	}

	private ProductQty getProductQtyByBarCode(String barCodeId, List<ProductQty> productQtyList) {
		for (int i = 0; i < productQtyList.size(); i++) {
			ProductQty li = productQtyList.get(i);
			if (barCodeId.equals(li.getProduct().getBarCodeId())) {
				return li;
			}
		}
		return null;
	}

	private Bill removeProductFromBill(Long billId, String barCodeId) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException(
					"Problem with input data: Bill ID  " + billId + " Bill ID does not exists in Bill");
		}
		Bill bill = optBill.get();
		List<ProductQty> productQtyList = bill.getProductQtyList();
		findProductByBarCode(barCodeId);
		if (productQtyList != null && !productQtyList.isEmpty()) {
			ProductQty productQty = getProductQtyByBarCode(barCodeId, productQtyList);
			if (null == productQty) {
				throw new CustomException(
						"Problem with input data: Product does not exist. Cannot remove product with BarCode ID "
								+ barCodeId);

			}
			productQtyList.remove(productQty);
			bill.setProductQtyList(productQtyList);
		} else {
			throw new CustomException(
					"Problem with input data:There are no Product in the Bill. Cannot remove product with BarCode ID "
							+ barCodeId);
		}
		billRepository.save(bill);
		return bill;
	}

	public Bill updateBill(BillInfoBean billUpdateInfo, Long billId) {
		if (null == billUpdateInfo) {
			throw new CustomException("There is no information to be updated for id " + billId);
		}
		checkBillById(billId);

		if (null != billUpdateInfo.getAddProducts()) {
			for (BillBean billBean : billUpdateInfo.getAddProducts()) {
				addProductToBill(billId, billBean.getBarCodeId(), billBean.getQuantity());
			}
		}
		if (null != billUpdateInfo.getDeleteProducts()) {
			for (BillBean billBean : billUpdateInfo.getDeleteProducts()) {
				removeProductFromBill(billId, billBean.getBarCodeId());
			}
		}
		Optional<Bill> optBill = billRepository.findById(billId);

		if (!optBill.isPresent()) {
			throw new CustomException(
					"Problem with input data: Bill ID  " + billId + " Bill ID does not exists in Billt");
		}

		Bill bill = optBill.get();
		calculateTotalValues(bill);
		return bill;
	}

	private void checkBillById(Long billId) {
		Optional<Bill> optBill = billRepository.findById(billId);
		if (!optBill.isPresent()) {
			throw new CustomException(
					"Problem with input data: Bill ID  " + billId + " Bill ID does not exists in Bill");
		}
	}

	private Product findProductByBarCode(String barCodeId) {
		List<Product> productList = productRepository.findProductByBarCodeId(barCodeId);
		if (null == productList || productList.isEmpty()) {
			throw new CustomException(
					"Problem with input data: BarCode ID " + barCodeId + " does not exist in Product");
		}
		return productList.get(0);
	}
}
