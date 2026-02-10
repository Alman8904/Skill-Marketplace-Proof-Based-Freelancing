package com.Skill.Marketplace.SM.DTO.SkillDTO;

import com.Skill.Marketplace.SM.DTO.CategoryDTO.CategoryResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SkillResponseDTO {
    private Long id;
    private String skillName;
    private CategoryResponseDTO category;
}
