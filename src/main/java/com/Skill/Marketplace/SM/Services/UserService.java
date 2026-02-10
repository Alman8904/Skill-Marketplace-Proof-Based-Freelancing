package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.DTO.UserDTO.CreateUserDTO;
import com.Skill.Marketplace.SM.DTO.UserDTO.UpdateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserType;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ForbiddenException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel createNewUser(CreateUserDTO dto) {

        log.info("Creating new user with username: {}", dto.getUsername());

        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            log.warn("User creation failed because username is missing");
            throw new BadRequestException("Username is required");
        }

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            log.warn("User creation failed because password is missing");
            throw new BadRequestException("Password is required");
        }

        UserModel user = new UserModel();

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if (dto.getUserType() == null) {
            user.setUserType(UserType.CONSUMER);
        } else {
            if (dto.getUserType() == UserType.ADMIN) {
                throw new ForbiddenException("Cannot create user with ADMIN role");
            }
            user.setUserType(dto.getUserType());
        }

        UserModel saved = userRepo.save(user);
        log.info("User created successfully : id={}, username: {}", saved.getId(), dto.getUsername());

        return saved;

    }

    public UserModel getUserProfile(String username) {
        return userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public UserModel updateUser(String username, UpdateUserDTO request) {
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getUserType() == UserType.ADMIN) {
            throw new BadRequestException("Cannot update user to ADMIN role");
        } else {
            user.setUserType(request.getUserType());
        }

        return userRepo.save(user);
    }

    public void deleteUser(String username) {

        if (!userRepo.existsByUsername(username)) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepo.deleteUserByUsername(username);
    }
}
