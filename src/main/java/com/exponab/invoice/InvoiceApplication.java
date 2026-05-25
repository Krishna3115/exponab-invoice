package com.exponab.invoice;

import com.exponab.invoice.entity.AppUser;
import com.exponab.invoice.entity.Role;

import com.exponab.invoice.repo.AppUserRepository;


import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class InvoiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvoiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner initAdminUser(
			AppUserRepository repository,
			PasswordEncoder passwordEncoder
	) {

		return args -> {

			// Check if admin already exists
			if (repository.findByUsername("admin").isEmpty()) {

				AppUser admin = new AppUser();

				admin.setUsername("admin");

				// Encrypt password
				admin.setPassword(
						passwordEncoder.encode("admin123")
				);

				admin.setRoles(Set.of(Role.ROLE_ADMIN));

				repository.save(admin);

				System.out.println("=================================");
				System.out.println("DEFAULT ADMIN CREATED");
				System.out.println("Username : admin");
				System.out.println("Password : admin123");
				System.out.println("=================================");
			}
		};
	}
}