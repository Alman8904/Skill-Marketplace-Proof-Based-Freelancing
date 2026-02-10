package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.DTO.SkillDTO.CreateSkillDTO;
import com.Skill.Marketplace.SM.DTO.SkillDTO.UpdateSkillDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import com.Skill.Marketplace.SM.Entities.Skill;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.CategoryRepo;
import com.Skill.Marketplace.SM.Repo.SkillsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class SkillService {

    @Autowired
    private SkillsRepo skillsRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public Skill create(CreateSkillDTO dto) {

        if (dto.getSkillName() == null || dto.getSkillName().isEmpty()) {
            throw new BadRequestException("Skill name is required");
        }

        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("No Category found"));

        Skill skill = new Skill();
        skill.setSkillName(dto.getSkillName());

        skill.setCategory(category);

        return skillsRepo.save(skill);
    }

    public Skill getById(Long id) {
        return skillsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No skills found"));
    }

    public Page<Skill> getAll(Pageable pageable) {
        return skillsRepo.findAll(pageable);
    }

    public Skill update(Long id, UpdateSkillDTO dto) {
        Skill skill = skillsRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No skills found"));

        if (dto.getSkillName() == null || dto.getSkillName().isEmpty()) {
            throw new BadRequestException("Skill name is required");
        }

        skill.setSkillName(dto.getSkillName());


        Category category = categoryRepo.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("No category found"));
        skill.setCategory(category);

        return skillsRepo.save(skill);

    }

    public void delete(Long id) {

        if (!skillsRepo.existsById(id)) {
            throw new ResourceNotFoundException("No skills found");
        }
        skillsRepo.deleteById(id);
    }
}
