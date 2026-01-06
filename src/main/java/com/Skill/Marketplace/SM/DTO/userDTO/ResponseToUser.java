package com.Skill.Marketplace.SM.DTO.userDTO;
import com.Skill.Marketplace.SM.Entities.UserType;
import lombok.Data;

@Data
public class ResponseToUser {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private UserType userType;

}
