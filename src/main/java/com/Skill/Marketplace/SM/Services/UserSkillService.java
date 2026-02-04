package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.UserSkillDTO.updateUserSkillDTO;
import com.Skill.Marketplace.SM.DTO.UserSkillDTO.AssignSkillDTO;
import com.Skill.Marketplace.SM.Entities.Skill;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Entities.UserSkill;
import com.Skill.Marketplace.SM.Entities.UserType;
import com.Skill.Marketplace.SM.Exception.ForbiddenException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.SkillsRepo;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import com.Skill.Marketplace.SM.Repo.UserSkillRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserSkillService {

    @Autowired
    private UserSkillRepo userSkillRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SkillsRepo skillsRepo;

    @Transactional
    public void assignSkills(String username, AssignSkillDTO dto) {

        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getUserType() == UserType.CONSUMER) {
            throw new ForbiddenException("Consumers cannot offer skills");
        }

        for (AssignSkillDTO.SkillData skillData : dto.getSkills()) {

            Skill skill = skillsRepo.findById(skillData.getSkillId())
                    .orElseThrow(() -> new ResourceNotFoundException("Skill not found"));

            UserSkill userSkill = new UserSkill();
            userSkill.setUser(user);
            userSkill.setSkill(skill);
            userSkill.setDescription(skillData.getDescription());
            userSkill.setRate(skillData.getRate());
            userSkill.setExperience(skillData.getExperience());
            userSkill.setServiceMode(skillData.getServiceMode());
            userSkill.setActive(true);

            userSkillRepo.save(userSkill);
        }
    }

    @Transactional
    public void deactivateUserSkill(Long userSkillId, String username) {
        UserSkill userSkill = userSkillRepo.findById(userSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found"));

        if (!userSkill.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("Unauthorized to deactivate this skill");
        }

        userSkill.setActive(false);
        userSkillRepo.save(userSkill);
    }

    @Transactional
    public void updateUserSkill(Long userSkillId, String username, updateUserSkillDTO skillData) {
        UserSkill userSkill = userSkillRepo.findById(userSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("UserSkill not found"));

        if (!userSkill.getUser().getUsername().equals(username)) {
            throw new ForbiddenException("Unauthorized to update this skill");
        }

        userSkill.setDescription(skillData.getDescription());
        userSkill.setRate(skillData.getRate());
        userSkill.setExperience(skillData.getExperience());
        userSkill.setServiceMode(skillData.getServiceMode());

        userSkillRepo.save(userSkill);
    }

    public List<UserSkill> getSkillsByUser(String username) {
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return userSkillRepo.findByUser(user);
    }

    public Page<UserSkill> searchProvidersBySkill(String skillName, Pageable pageable) {
        return userSkillRepo.findBySkill_SkillNameContainingIgnoreCase(skillName,pageable);
    }



}
