package com.security.duanspringboot;

import com.security.duanspringboot.entity.UserModel;
import com.security.duanspringboot.entity.UserRoleModel;
import com.security.duanspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class DuanSpringbootApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(DuanSpringbootApplication.class, args);
    }

    @Override
    public void run(String... args) {
        UserModel adminAccount = userRepository.findByUserRoleModel(UserRoleModel.ADMIN);

        if (adminAccount == null) {
            UserModel userModel = new UserModel();

            userModel.setUserId(UUID.randomUUID().toString().replaceAll("-",""));
            userModel.setEmail("admin@gmail.com");
            userModel.setFirstName("Le");
            userModel.setLastName("Nguyen");
            userModel.setUserRoleModel(UserRoleModel.ADMIN);
            userModel.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(userModel);
        }
    }
}
