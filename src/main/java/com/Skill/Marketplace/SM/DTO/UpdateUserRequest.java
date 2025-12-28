package com.Skill.Marketplace.SM.DTO;
import com.Skill.Marketplace.SM.Entities.UserType;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private UserType userType;
}
