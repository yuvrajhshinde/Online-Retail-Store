package com.onlineretail.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineretail.beans.ProductBean;
import com.onlineretail.dao.entity.Product;
import com.onlineretail.dao.repository.ProductRepository;
import com.onlineretail.exception.CustomException;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	/**
	 * 
	 * @param productBean
	 * @return
	 */
	public Product createProduct(ProductBean productBean) {

		checkProductByBarCode(productBean.getBarCodeId());
		Product product = new Product();
		product.setBarCodeId(productBean.getBarCodeId());
		product.setPrice(productBean.getPrice());
		product.setName(productBean.getName());
		product.setCategory(productBean.getCategory());

		product = productRepository.save(product);
		return product;

	}

	/**
	 * 
	 * @param id
	 */
	public void deleteProduct(Long id) {
		checkProductByID(id);
		productRepository.deleteById(id);
	}

	/**
	 * 
	 * @return
	 */
	public Iterable<Product> getAllProducts() {
		Iterable<Product> products = productRepository.findAll();
		return products;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductById(Long id) {
		Optional<Product> optProduct = productRepository.findById(id);
		if (!optProduct.isPresent()) {
			throw new CustomException(
					"Problem with input data: Product ID  " + id + " Product ID does not exists in Product");
		}

		return optProduct.get();
	}

	/**
	 * 
	 * @param productBean
	 * @param id
	 * @return
	 */
	public Product updateProduct(ProductBean productBean, Long id) {
		Optional<Product> optProduct = productRepository.findById(id);
		if (!optProduct.isPresent()) {
			throw new CustomException(
					"Problem with input data: Product ID  " + id + " Product ID does not exists in Product");
		}

		Product product = optProduct.get();
		product.setBarCodeId(productBean.getBarCodeId());
		product.setPrice(productBean.getPrice());
		Product resultProduct = productRepository.save(product);
		product.setName(productBean.getName());
		product.setCategory(productBean.getCategory());

		return resultProduct;
	}

	/**
	 * 
	 * @param barCodeId
	 */
	private void checkProductByBarCode(String barCodeId) {
		List<Product> productsByBarCodeID = productRepository.findProductByBarCodeId(barCodeId);
		if (null != productsByBarCodeID && !productsByBarCodeID.isEmpty()) {
			throw new CustomException(
					"Problem with input data: BarCode ID  " + barCodeId + " already exists in Product Master");
		}
	}

	/**
	 * 
	 * @param id
	 */
	private void checkProductByID(Long id) {
		Optional<Product> optProduct = productRepository.findById(id);
		if (!optProduct.isPresent()) {
			throw new CustomException(
					"Problem with input data: Product ID  " + id + " Product ID does not exists in Product");
		}
	}

}
