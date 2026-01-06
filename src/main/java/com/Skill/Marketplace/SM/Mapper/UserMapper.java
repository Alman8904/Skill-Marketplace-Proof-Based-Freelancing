package com.Skill.Marketplace.SM.Mapper;
import com.Skill.Marketplace.SM.DTO.userDTO.ResponseToUser;
import com.Skill.Marketplace.SM.Entities.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public ResponseToUser toResponse(UserModel user) {
        ResponseToUser response = new ResponseToUser();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setUserType(user.getUserType());
        return response;
    }
}
