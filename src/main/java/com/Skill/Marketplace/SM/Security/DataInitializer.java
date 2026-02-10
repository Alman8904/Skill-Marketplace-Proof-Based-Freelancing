package com.Skill.Marketplace.SM.Security;

import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserType;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

//    @Value("${app.admin.email}")
//    private String adminEmail;

    @PostConstruct
    public void init() {

        if (userRepo.getUserByUsername(adminUsername).isEmpty()) {
            UserModel admin = new UserModel();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setUserType(UserType.ADMIN);

            userRepo.save(admin);
        }
    }
}
