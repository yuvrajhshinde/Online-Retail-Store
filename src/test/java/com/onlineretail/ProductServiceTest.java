package com.onlineretail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.onlineretail.beans.ProductBean;
import com.onlineretail.dao.entity.Product;
import com.onlineretail.service.ProductService;
import com.onlineretail.util.Category;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Test
	public void testCreateProduct() {
		Product product = productService.createProduct(new ProductBean("TestProduct1", "AAAA-1111", 10, Category.A));
		Product resultProduct = productService.getProductById(product.getProductId());
		assertThat(product.getProductId()).isEqualTo(resultProduct.getProductId());
	}

	@Test
	public void testCreateUpdate() {

		Product product = productService.createProduct(new ProductBean("TestProduct2", "BBBB-2222", 20, Category.A));
		Product updatedProduct = productService
				.updateProduct(new ProductBean("TestProduct2", "BBBB-5555", 30, Category.A), product.getProductId());
		assertThat("BBBB-5555".equals(updatedProduct.getBarCodeId()));
	}

	@Test
	public void testCreateDelete() {
		Product createdProduct = productService
				.createProduct(new ProductBean("TestProduct3", "CCCC-5555", 20, Category.A));
		productService.deleteProduct(createdProduct.getProductId());
		Product deletedProduct = productService.getProductById(createdProduct.getProductId());
		assertThat(deletedProduct).isNull();
	}

}
