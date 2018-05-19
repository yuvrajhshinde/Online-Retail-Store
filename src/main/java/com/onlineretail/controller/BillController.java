package com.onlineretail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.onlineretail.beans.BillInfoBean;
import com.onlineretail.dao.entity.Bill;
import com.onlineretail.service.BillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(value = "Online retail Manage Bill")
public class BillController {

	@Autowired
	private BillService billService;

	@ApiOperation(produces = "application/json", value = "Create Bill")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Bill details") })
	@RequestMapping(value = "/bills", method = RequestMethod.POST)
	public ResponseEntity<Bill> createBill() {
		Bill bill = billService.createBill(new Bill(0, 0, 0, 0));
		return new ResponseEntity<>(bill, new HttpHeaders(), HttpStatus.CREATED);
	}

	@ApiOperation(produces = "application/json", value = "Delete Bill")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Status of request"),
			@ApiResponse(code = 404, message = "Bill does not exist") })
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteBill(@PathVariable Long id) {
		billService.deleteBill(id);
		return new ResponseEntity<>("{\"status\": \"success\"}", HttpStatus.OK);
	}

	@ApiOperation(produces = "application/json", value = "fetch bill data ")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list") })
	@RequestMapping(value = "/bills", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Bill>> getAllBills() {
		return new ResponseEntity<>(billService.getAllBills(), HttpStatus.OK);
	}

	@ApiOperation(produces = "application/json", value = "find Bill by Id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved Bill details"),
			@ApiResponse(code = 404, message = "Bill Not Found") })
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.GET)
	public ResponseEntity<Bill> getBillById(@PathVariable Long id) {
		return new ResponseEntity<>(billService.getBillById(id), HttpStatus.OK);
	}

	@ApiOperation(produces = "application/json", value = "Update products from Bill")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Bill details"),
			@ApiResponse(code = 404, message = "Validation error") })
	@RequestMapping(value = "/bills/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Bill> updateBill(@RequestBody BillInfoBean billInfoBean, @PathVariable Long id) {
		Bill updated = billService.updateBillData(billInfoBean, id);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

}
