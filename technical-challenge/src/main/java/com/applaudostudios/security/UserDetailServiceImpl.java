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
import com.applaudostudios.entities.Role;
import com.applaudostudios.repositories.AccountRepository;
import com.applaudostudios.repositories.RoleRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Account currentUser = accountRepository.findByUsername(username);
		List<Role> userRoles = roleRepository.findByAccount(currentUser);
		String[] roles = userRoles.stream().map(Role::getName).collect(Collectors.toList()).toArray(new String[0]);

		if (currentUser == null) {
			throw new UsernameNotFoundException(username);
		}

		return new UserDetailsImpl(currentUser, AuthorityUtils.createAuthorityList(roles));
	}

}
