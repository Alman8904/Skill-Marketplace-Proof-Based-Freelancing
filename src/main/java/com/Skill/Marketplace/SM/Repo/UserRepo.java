package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserModel,Long> {

     Optional<UserModel> getUserByUsername(String name);

     Optional <UserModel> deleteUserByUsername(String username);

     boolean existsByUsername(String username);

}
