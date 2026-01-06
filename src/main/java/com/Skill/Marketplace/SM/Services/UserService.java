package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.userDTO.CreateUserDTO;
import com.Skill.Marketplace.SM.DTO.userDTO.UpdateUserDTO;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Repo.SkillsRepo;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import com.Skill.Marketplace.SM.Repo.UserSkillsRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SkillsRepo skillsRepo;

    @Autowired
    private UserSkillsRepo userSkillsRepo;

    public UserModel createNewUser(CreateUserDTO dto){

        UserModel user = new UserModel();

        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setUserType(dto.getUserType());

        return userRepo.save(user);
    }

    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }

    public UserModel updateUser(Long id , UpdateUserDTO request){
         UserModel user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUserType(request.getUserType());

        return userRepo.save(user);
    }

    public UserModel getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }

    public List<UserModel> getAllUsers(){
        return userRepo.findAll();
    }

    public UserModel getUserByUsername(String username){
        return userRepo.getUserByUsername(username)
                .orElseThrow(()-> new RuntimeException("User not found"));
    }


}
