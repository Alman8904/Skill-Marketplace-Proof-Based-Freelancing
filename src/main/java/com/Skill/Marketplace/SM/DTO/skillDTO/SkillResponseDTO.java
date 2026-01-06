package com.Skill.Marketplace.SM.DTO.skillDTO;
import com.Skill.Marketplace.SM.DTO.categoryDTO.CategoryResponseDTO;
import lombok.Data;

@Data
public class SkillResponseDTO {
    private Long id;
    private String skillName;
    private CategoryResponseDTO category;
}
