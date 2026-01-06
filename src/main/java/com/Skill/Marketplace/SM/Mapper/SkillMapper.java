package com.Skill.Marketplace.SM.Mapper;
import com.Skill.Marketplace.SM.DTO.skillDTO.SkillResponseDTO;
import com.Skill.Marketplace.SM.Entities.Skills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SkillMapper {

    @Autowired
    private CategoryMapper categoryMapper;

    public SkillResponseDTO toResponse(Skills skills){
        SkillResponseDTO response = new SkillResponseDTO();

        response.setId(skills.getId());
        response.setSkillName(skills.getSkillName());
        response.setCategory(categoryMapper.toResponse(skills.getCategory()));

        return response;
    }
}
