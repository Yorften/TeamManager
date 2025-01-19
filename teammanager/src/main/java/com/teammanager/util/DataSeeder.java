package com.teammanager.util;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import com.teammanager.model.*;
import com.teammanager.model.enums.EmploymentStatus;
import com.teammanager.repository.EmployeeRepository;
import com.teammanager.repository.RoleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DataSeeder {

	private RoleRepository roleRepository;
	private EmployeeRepository employeeRepository;
	private BCryptPasswordEncoder passwordEncoder;

	public void seedDatabase(int max) {
		Faker faker = new Faker(new Locale("en-US"));

		Instant start = Instant.now();

		// Create roles
		Role adminRole = roleRepository.save(Role.builder().name("ROLE_ADMIN").build());
		Role managerRole = roleRepository.save(Role.builder().name("ROLE_MANAGER").build());
		Role hrRole = roleRepository.save(Role.builder().name("ROLE_HR").build());
		roleRepository.save(Role.builder().name("ROLE_EMPLOYEE").build());

		log.info("Seeding started...");

		// Create admin user and employee
		User adminUser = User.builder()
				.email("admin@company.com")
				.username("admin")
				.password(passwordEncoder.encode("password"))
				.role(adminRole)
				.build();

		Employee adminEmployee = Employee.builder()
				.fullName("System Admin")
				.jobTitle("System Administrator")
				.department(Departments.ADMINISTRATION.name())
				.hireDate(LocalDate.now())
				.employmentStatus(EmploymentStatus.FULL_TIME)
				.contactInformation(generatePhoneNumber())
				.address(faker.address().fullAddress())
				.user(adminUser)
				.build();

		employeeRepository.save(adminEmployee);

		// Create other employees
		for (int i = 1; i < max; i++) {
			Employee employee = Employee.builder()
					.fullName(faker.name().fullName())
					.jobTitle(faker.job().title())
					.department(getRandomDepartment())
					.hireDate(LocalDate.now().minusDays(faker.number().numberBetween(1, 365)))
					.employmentStatus(EmploymentStatus.FULL_TIME)
					.contactInformation(generatePhoneNumber())
					.address(faker.address().fullAddress())
					.build();

			// Create user account only for managers and HR
			if (i % 7 == 0) { // Managers
				User user = User.builder()
						.email(faker.internet().emailAddress())
						.username(faker.name().username())
						.password(passwordEncoder.encode("password"))
						.role(managerRole)
						.build();

				employee.setUser(user);
				employee.setJobTitle("Department Manager");
			} else if (i % 9 == 0) { // HR
				User user = User.builder()
						.email(faker.internet().emailAddress())
						.username(faker.name().username())
						.password(passwordEncoder.encode("password"))
						.role(hrRole)
						.build();

				employee.setUser(user);
				employee.setJobTitle("HR Specialist");
				employee.setDepartment(Departments.HUMAN_RESOURCES.name());

			}

			employeeRepository.save(employee);
		}

		log.info("Seeding completed. Created {} records", max);

		long timeTaken = (Instant.now().toEpochMilli() - start.toEpochMilli()) / 1000;

		log.info("Seeding complete : time taken " + timeTaken + " s");

	}

	private String getRandomDepartment() {
		Departments[] departments = Departments.values();
		int randomIndex = new Random().nextInt(departments.length);
		return departments[randomIndex].name();
	}

	private String generatePhoneNumber() {
		Random random = new Random();
		StringBuilder phoneNumber = new StringBuilder("+");

		// Add country code (e.g., +232)
		phoneNumber.append(random.nextInt(900) + 100);

		// Add 9-digit phone number
		for (int i = 0; i < 9; i++) {
			phoneNumber.append(random.nextInt(10));
		}

		return phoneNumber.toString();
	}

}
