package com.applaudostudios.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.applaudostudios.entities.TransactionLog;
import com.applaudostudios.services.TransactionLogService;

/**
 * TransactionLogController is only get log of all rentals and purchases; this
 * is exposed on URL http://serve-host:port/api/transaction-logs
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RestController
@RequestMapping(value = "/api")
public class TransactionLogController {

	@Autowired
	private TransactionLogService transactionLogService;

	@GetMapping("/transaction-logs")
	@ResponseBody
	public Page<TransactionLog> getTransactionLogs(Pageable pageable) {
		return transactionLogService.getTransactionLogs(pageable);
	}

}
