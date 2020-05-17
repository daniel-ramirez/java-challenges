package com.applaudostudios.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.applaudostudios.entities.Account;
import com.applaudostudios.entities.AccountRole;

/**
 * AccountRoleRepository is a rest repository and this is exposed
 * on URL http://serve-host:port/api/account-roles
 * 
 * @author      Daniel Ramirez
 * @since       0.0.1
 */
@RepositoryRestResource(path = "account-roles", collectionResourceRel = "accountRoles")
public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {

	List<AccountRole> findByAccount(Account account);

}
