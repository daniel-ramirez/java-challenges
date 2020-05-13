package com.applaudostudios.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.applaudostudios.entities.Account;

/**
 * AccountRepository is a rest repository and this is exposed
 * on URL http://serve-host:port/api/accounts
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RepositoryRestResource(path = "accounts", collectionResourceRel = "accounts")
public interface AccountRepository extends JpaRepository<Account, Long> {

}
