package com.Skill.Marketplace.SM.DTO.UserDTO;

import com.Skill.Marketplace.SM.Entities.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUserDTO {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20)
    private String username;
    private String firstName;
    private String lastName;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8)
    private String password;
    @NotNull(message = "User type is mandatory")
    private UserType userType;
}
