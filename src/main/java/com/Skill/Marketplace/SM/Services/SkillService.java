package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.skillDTO.CreateSkillDTO;
import com.Skill.Marketplace.SM.DTO.skillDTO.UpdateSkillDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import com.Skill.Marketplace.SM.Entities.Skills;
import com.Skill.Marketplace.SM.Repo.CategoryRepo;
import com.Skill.Marketplace.SM.Repo.SkillsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillsRepo skillsRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Skills create(CreateSkillDTO dto){

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(()-> new RuntimeException("Not found"));

        Skills skills = new Skills();
        skills.setSkillName(dto.getSkillName());

        skills.setCategory(category);

        return skillsRepo.save(skills);
    }

    public Skills update(Long id ,UpdateSkillDTO dto){
        Skills skills = skillsRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("No skills found"));

        skills.setSkillName(dto.getSkillName());


        Category category =  categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(()-> new RuntimeException("No category found"));
        skills.setCategory(category);

        return skillsRepo.save(skills);

    }

    public Skills getById(Long id){
        return skillsRepo.findById(id)
                .orElseThrow(()->new RuntimeException("No skills found"));
    }

    public List<Skills> getAll(){
        return skillsRepo.findAll();
    }

    public void delete(Long id){
        skillsRepo.deleteById(id);
    }
}
