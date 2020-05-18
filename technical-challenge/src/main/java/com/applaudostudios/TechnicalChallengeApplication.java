package com.applaudostudios;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.applaudostudios.entities.Account;
import com.applaudostudios.entities.AccountRole;
import com.applaudostudios.entities.Role;
import com.applaudostudios.repositories.AccountRepository;
import com.applaudostudios.repositories.AccountRoleRepository;
import com.applaudostudios.repositories.RoleRepository;

@SpringBootApplication
public class TechnicalChallengeApplication {

	private final Log logger = LogFactory.getLog(TechnicalChallengeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TechnicalChallengeApplication.class, args);
	}

	@Bean
	ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository,
			AccountRoleRepository accountRoleRepository) {
		return args -> {
			try {
				Account account1 = new Account();
				account1.setUsername("admin");
				account1.setPassword((new BCryptPasswordEncoder()).encode("123456"));
				account1 = accountRepository.save(account1);

				Role role1 = new Role();
				role1.setName("ROLE_ADMIN");
				role1 = roleRepository.save(role1);

				AccountRole accountRole1 = new AccountRole();
				accountRole1.setAccount(account1);
				accountRole1.setRole(role1);
				accountRoleRepository.save(accountRole1);
			} catch (Exception e) {
				logger.info("Admin - user or role already exists");
			}

			try {
				Account account2 = new Account();
				account2.setUsername("user");
				account2.setPassword((new BCryptPasswordEncoder()).encode("123456"));
				account2 = accountRepository.save(account2);

				Role role2 = new Role();
				role2.setName("ROLE_USER");
				role2 = roleRepository.save(role2);

				AccountRole accountRole2 = new AccountRole();
				accountRole2.setAccount(account2);
				accountRole2.setRole(role2);
				accountRoleRepository.save(accountRole2);
			} catch (Exception e) {
				logger.info("User - user or role already exists");
			}
		};
	}

}
