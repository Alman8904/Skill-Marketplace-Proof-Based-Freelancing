package com.Skill.Marketplace.SM.DTO.UserDTO;

import com.Skill.Marketplace.SM.Entities.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseToUser {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private UserType userType;

}
