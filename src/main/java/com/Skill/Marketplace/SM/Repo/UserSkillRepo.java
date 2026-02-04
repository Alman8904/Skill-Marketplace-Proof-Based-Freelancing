package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.Skill;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepo extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUser(UserModel user);

    Optional<UserSkill> findByUserAndSkill(UserModel user, Skill skill);


    @Query("""
    SELECT us FROM UserSkill us
    JOIN us.skill s
    JOIN us.user u
    WHERE LOWER(s.skillName) LIKE LOWER(CONCAT('%', :skillName, '%'))
""")
    Page<UserSkill> findBySkill_SkillNameContainingIgnoreCase(String skillName, Pageable pageable);




}
