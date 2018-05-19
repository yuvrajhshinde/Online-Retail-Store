package com.onlineretail.dao.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.onlineretail.dao.entity.Bill;

@Repository
public interface BillRepository extends CrudRepository<Bill, Long> {

}
