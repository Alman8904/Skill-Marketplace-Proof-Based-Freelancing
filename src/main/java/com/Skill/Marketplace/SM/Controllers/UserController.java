package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.userDTO.*;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Mapper.UserMapper;
import com.Skill.Marketplace.SM.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/public/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ResponseToUser> createUser (@RequestBody CreateUserDTO request){
        return ResponseEntity.ok(userMapper.toResponse(userService.createNewUser(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseToUser> updateUser(@PathVariable Long id, @RequestBody UpdateUserDTO updates){
        return ResponseEntity.ok(userMapper.toResponse(userService.updateUser(id,updates)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseToUser> searchUser(@PathVariable Long id){
        return ResponseEntity.ok(userMapper.toResponse(userService.getUserById(id)));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ResponseToUser> searchUserByUsername(@PathVariable String username){
        return ResponseEntity.ok(userMapper.toResponse(userService.getUserByUsername(username)));
    }

    @GetMapping("/admin")
    public ResponseEntity<List<ResponseToUser>> getAllUsers(){
        List<UserModel> users = userService.getAllUsers();
        List<ResponseToUser> response = users.stream()
                .map(userMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }
}
