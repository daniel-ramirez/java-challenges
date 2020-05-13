package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.applaudostudios.entities.PurchaseOrder;

/**
 * PurchaseOrderRepository is a rest repository and this is exposed
 * on URL http://serve-host:port/api/purchase-order
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RepositoryRestResource(path = "purchase-orders", collectionResourceRel = "purchaseOrders")
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
