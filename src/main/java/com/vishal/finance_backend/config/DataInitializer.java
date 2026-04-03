package com.vishal.finance_backend.config;

import com.vishal.finance_backend.Entity.Role;
import com.vishal.finance_backend.Entity.User;
import com.vishal.finance_backend.enums.RoleName;
import com.vishal.finance_backend.repository.RoleRepository;
import com.vishal.finance_backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
 @Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initData(RoleRepository roleRepository,
                               UserRepository userRepository,
                               PasswordEncoder passwordEncoder){
        return args ->{
//            role create
            if(roleRepository.findByName(RoleName.ADMIN).isEmpty()){
                Role adminRole = new Role();
                adminRole.setName(RoleName.ADMIN);
                roleRepository.save(adminRole);

                Role analystRole = new Role();
                analystRole.setName(RoleName.ANALYST);
                roleRepository.save(analystRole);

                Role viewerRole = new Role();
                viewerRole.setName(RoleName.VIEWER);
                roleRepository.save(viewerRole);
            }
//            admin demo user
            if (userRepository.findByUsername("admin").isEmpty()) {

                Role adminRole = roleRepository.findByName(RoleName.ADMIN).get();

                User admin = new User();
                admin.setName("Admin User");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setActive(true);
                admin.setRole(adminRole);

                userRepository.save(admin);
            }
//            analyst demo user
            if (userRepository.findByUsername("analyst").isEmpty()) {

                Role analystRole = roleRepository.findByName(RoleName.ANALYST).get();

                User analyst = new User();
                analyst.setName("Analyst User");
                analyst.setUsername("analyst");
                analyst.setPassword(passwordEncoder.encode("analyst123"));
                analyst.setActive(true);
                analyst.setRole(analystRole);

                userRepository.save(analyst);
            }
//            viewer demo create
            if (userRepository.findByUsername("viewer").isEmpty()) {

                Role viewerRole = roleRepository.findByName(RoleName.VIEWER).get();

                User viewer = new User();
                viewer.setName("Viewer User");
                viewer.setUsername("viewer");
                viewer.setPassword(passwordEncoder.encode("viewer123"));
                viewer.setActive(true);
                viewer.setRole(viewerRole);

                userRepository.save(viewer);
            }
            System.out.println("Defalt rols and user initialz");
        };
    }
}
