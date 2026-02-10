package com.Skill.Marketplace.SM.Controllers;

import com.Skill.Marketplace.SM.DTO.UserDTO.CreateUserDTO;
import com.Skill.Marketplace.SM.DTO.UserDTO.LoginDTO;
import com.Skill.Marketplace.SM.DTO.UserDTO.ResponseToUser;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Services.AuthService;
import com.Skill.Marketplace.SM.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO request) {

        UserModel user = userService.createNewUser(request);
        return ResponseEntity.ok(
                new ResponseToUser(
                        user.getId(),
                        user.getUsername(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getUserType()
                )
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO request) {

        String token = authService.login(request.getUsername(), request.getPassword());

        return ResponseEntity.ok(token);
    }
}

