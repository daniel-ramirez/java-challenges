package com.applaudostudios.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import com.applaudostudios.entities.PurchaseOrderDetail;
import com.applaudostudios.entities.TransactionLog;
import com.applaudostudios.repositories.TransactionLogRepository;

/**
 * TransactionEventHandler is an Event Handler to track all rentals and
 * purchases (who bought, how many and when).
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Component
@RepositoryEventHandler(PurchaseOrderDetail.class)
public class TransactionLogEventHandler {

	public static final String TRX_TYPE_RENTAL = "rental";

	public static final String TRX_TYPE_PURCHASE = "purchase";

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@HandleAfterCreate
	public void handleBeforeCreate(PurchaseOrderDetail purchaseOrderDetail) {
		String transactionType = (purchaseOrderDetail.isRenting()) ? TRX_TYPE_RENTAL : TRX_TYPE_PURCHASE;
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setUserInfo(purchaseOrderDetail.getPurchaseOrder().getUserInfo());
		transactionLog.setTransactionType(transactionType);
		transactionLog.setQuantity(purchaseOrderDetail.getQuantity());
		transactionLog.setTransactionDate(purchaseOrderDetail.getCreatedDate());
		transactionLogRepository.save(transactionLog);
	}

}
