package com.Skill.Marketplace.SM.DTO.UserDTO;

import com.Skill.Marketplace.SM.Entities.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 8)
    private String password;
    private String firstName;
    private String lastName;
    @NotNull(message = "User type is required")
    private UserType userType;
}
