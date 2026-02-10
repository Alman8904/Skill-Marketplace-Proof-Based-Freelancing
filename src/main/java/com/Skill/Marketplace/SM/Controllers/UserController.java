package com.Skill.Marketplace.SM.Controllers;

import com.Skill.Marketplace.SM.DTO.UserDTO.ResponseToUser;
import com.Skill.Marketplace.SM.DTO.UserDTO.UpdateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Services.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Data
@RestController
@RequestMapping("/public/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ResponseEntity<?> searchUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserModel user = userService.getUserProfile(username);
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

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserModel updatedUser = userService.updateUser(username, request);
        return ResponseEntity.ok(
                new ResponseToUser(
                        updatedUser.getId(),
                        updatedUser.getUsername(),
                        updatedUser.getFirstName(),
                        updatedUser.getLastName(),
                        updatedUser.getUserType()
                )
        );
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.deleteUser(username);
        return ResponseEntity.noContent().build();
    }


}
