package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.userDTO.CreateUserDTO;
import com.Skill.Marketplace.SM.DTO.userDTO.UpdateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserType;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel createNewUser(CreateUserDTO dto){

        if(dto.getUsername()==null || dto.getUsername().isEmpty()){
            throw new BadRequestException("Username is required");
        }

        if(dto.getPassword()==null || dto.getPassword().isEmpty()){
            throw new BadRequestException("Password is required");
        }

        UserModel user = new UserModel();

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        if(dto.getUserType()==null){
            user.setUserType(UserType.CONSUMER);
            }
        else{
            if(dto.getUserType()==UserType.ADMIN){
                throw new RuntimeException("Cannot create user with ADMIN role");
            }
            user.setUserType(dto.getUserType());
        }
        return userRepo.save(user);
    }

    public UserModel getUserProfile(String username){
        return userRepo.getUserByUsername(username)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));
    }

    public UserModel updateUser(String username , UpdateUserDTO request){
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if(request.getUserType()==UserType.ADMIN){
            throw new BadRequestException("Cannot update user to ADMIN role");
        }
        else{
            user.setUserType(request.getUserType());
        }

        return userRepo.save(user);
    }

    public void deleteUser(String username){

        if(!userRepo.existsByUsername(username)){
            throw new ResourceNotFoundException("User not found");
        }
        userRepo.deleteUserByUsername(username);
    }
}
