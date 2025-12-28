package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.UpdateUserRequest;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public void createNewUser(UserModel user){
        userRepo.save(user);
    }

    public void deleteUser(UserModel user){
        userRepo.delete(user);
    }

    public UserModel updateUser(Long id , UpdateUserRequest request){
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
