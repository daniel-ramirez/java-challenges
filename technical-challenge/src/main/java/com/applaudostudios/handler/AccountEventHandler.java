package com.applaudostudios.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.applaudostudios.entities.Account;
import com.applaudostudios.repositories.AccountRepository;

@Component
@RepositoryEventHandler(Account.class)
public class AccountEventHandler {

	@Autowired
	private AccountRepository accountRepository;

	@HandleBeforeCreate
	public void handleUserCreate(Account account) {
		account.setPassword((new BCryptPasswordEncoder()).encode(account.getPassword()));
	}

	@HandleBeforeSave
	public void handleUserUpdate(Account account) {
		if (account.getPassword() == null || account.getPassword().equals("")) {
			// keeps the last password
			Account storedAccount = accountRepository.getOne(account.getId());
			account.setPassword(storedAccount.getPassword());
		} else {
			// password change request
			account.setPassword((new BCryptPasswordEncoder()).encode(account.getPassword()));
		}
	}

}
