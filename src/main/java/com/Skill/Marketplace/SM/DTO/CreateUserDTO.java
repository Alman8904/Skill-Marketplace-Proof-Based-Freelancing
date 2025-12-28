package com.Skill.Marketplace.SM.DTO;
import com.Skill.Marketplace.SM.Entities.UserType;
import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private UserType userType;
}
