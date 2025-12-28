package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.CreateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserSkills;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import com.Skill.Marketplace.SM.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserDTO> createUser (@RequestBody CreateUserDTO request){

        UserModel user = new UserModel();

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(request.getPassword());
        user.setUserType(request.getUserType());

        userService.createNewUser(user);


    }

}
