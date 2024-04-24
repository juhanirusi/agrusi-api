package com.agrusi.backendapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApiApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(
//			RoleRepository roleRepository
//	) {
//		return args -> {
//
//			if (roleRepository.findByAuthority(EAccountRole.ADMIN).isPresent()) {
//				System.out.println("ADMIN role already present in the database");
//			} else {
//				roleRepository.save(new Role(EAccountRole.ADMIN));
//			}
//
//			if (roleRepository.findByAuthority(EAccountRole.MODERATOR).isPresent()) {
//				System.out.println("MODERATOR role already present in the database");
//			} else {
//				roleRepository.save(new Role(EAccountRole.MODERATOR));
//			}
//
//			if (roleRepository.findByAuthority(EAccountRole.USER).isPresent()) {
//				System.out.println("USER role already present in the database");
//			} else {
//				roleRepository.save(new Role(EAccountRole.USER));
//			}
//		};
//	}
}
