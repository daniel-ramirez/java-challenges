package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.applaudostudios.entities.TransactionLog;

/**
 * TransactionLogRepository is repository to log of all rentals and purchases
 * and this is not exposed in the API
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@Repository
public interface TransactionLogRepository extends JpaRepository<TransactionLog, Long> {

}
