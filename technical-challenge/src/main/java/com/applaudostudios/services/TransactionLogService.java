package com.applaudostudios.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.applaudostudios.entities.TransactionLog;
import com.applaudostudios.repositories.TransactionLogRepository;

/**
 * TransactionLogService is used to manage process to get log of all rentals and
 * purchases
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Service
public class TransactionLogService {

	private final Log logger = LogFactory.getLog(TransactionLogService.class);

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	/**
	 * Get list of change logs for rentals and purchases
	 * 
	 * @param pageable
	 */
	public Page<TransactionLog> getTransactionLogs(Pageable pageable) {
		try {
			return transactionLogRepository.findAll(pageable);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
