package com.onlineretail.dao.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlineretail.dao.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	public List<Product> findProductByBarCodeId(String barCodeId);
	public long count();
}
