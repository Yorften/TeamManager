package com.teammanager.util;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import com.teammanager.model.*;
import com.teammanager.repository.RoleRepository;
import com.teammanager.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DataSeeder {

	private RoleRepository roleRepository;
	private UserRepository userRepository;
	private BCryptPasswordEncoder passwordEncoder;

	public void seedDatabase(int max) {
		Faker faker = new Faker(new Locale("en-US"));

		Role role1 = Role.builder().name("ROLE_ADMIN").build();
		Role role2 = Role.builder().name("ROLE_MANAGER").build();
		Role role3 = Role.builder().name("ROLE_HR").build();
		Role role4 = Role.builder().name("ROLE_EMPLOYEE").build();
		roleRepository.saveAll(Arrays.asList(role1, role2, role3, role4));

		Instant start = Instant.now();

		CompletableFuture<Void> usersFuture = CompletableFuture.runAsync(() -> {
			log.info("Seeding users started...");

			Role adminRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new RuntimeException("Admin role not found"));
			Role managerRole = roleRepository.findByName("ROLE_MANAGER")
					.orElseThrow(() -> new RuntimeException("User role not found"));
			Role hrRole = roleRepository.findByName("ROLE_HR")
					.orElseThrow(() -> new RuntimeException("User role not found"));
			Role employeeRole = roleRepository.findByName("ROLE_EMPLOYEE")
					.orElseThrow(() -> new RuntimeException("User role not found"));

			// Parallel stream to reduce time taken in hashing the passwords
			List<User> users = IntStream.range(0, max).parallel()
					.mapToObj(i -> User.builder()
							.email(faker.internet().emailAddress())
							.username(faker.name().username())
							.password(passwordEncoder.encode("password")) // Expensive Operation
							.roles(i == 0 ? new HashSet<>(Arrays.asList(adminRole)) // First user is admin
									: (i % 7 == 0 ? new HashSet<>(Arrays.asList(managerRole)) // Every 7th user is a manager
											: (i % 9 == 0 ? new HashSet<>(Arrays.asList(hrRole)) // Every 9th user is a manager
													: new HashSet<>(Arrays.asList(employeeRole))))) // The rest is employees
							.build())
					.collect(Collectors.toList());
			userRepository.saveAll(users);

			log.info("Seeding users completed");
		});

		// CompletableFuture<Void> productsFuture = CompletableFuture.runAsync(() -> {
		// log.info("Seeding products started...");
		// Random random = new Random();
		// List<Product> products = IntStream.range(0, max)
		// .mapToObj(i -> Product.builder()
		// .designation(faker.lorem().word())
		// .price(faker.number().randomDouble(2, 10, 400))
		// .quantity(faker.number().numberBetween(20, 100))
		// .category(categories.get(random.nextInt(categories.size())))
		// .build())
		// .collect(Collectors.toList());
		// productRepository.saveAll(products);

		// log.info("Seeding products completed");
		// });

		CompletableFuture.allOf(usersFuture).join(); // Ensures both tasks are completed

		long timeTaken = (Instant.now().toEpochMilli() - start.toEpochMilli()) / 1000;

		log.info("Seeding complete : time taken " + timeTaken + " s");

	}

}
