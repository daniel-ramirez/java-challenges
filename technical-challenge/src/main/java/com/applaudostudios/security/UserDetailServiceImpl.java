package com.applaudostudios.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.applaudostudios.entities.Account;
import com.applaudostudios.entities.AccountRole;
import com.applaudostudios.entities.Role;
import com.applaudostudios.repositories.AccountRepository;
import com.applaudostudios.repositories.AccountRoleRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountRoleRepository accountRoleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Account currentUser = accountRepository.findByUsername(username);
		List<AccountRole> userRoles = accountRoleRepository.findByAccount(currentUser);
		String[] roles = userRoles.stream().map(AccountRole::getRole).map(Role::getName).collect(Collectors.toList())
				.toArray(new String[0]);

		if (currentUser == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(currentUser, AuthorityUtils.createAuthorityList(roles));
	}

}
