package com.Skill.Marketplace.SM.DTO.SkillDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSkillDTO {

    @NotBlank(message = "Skill name is required")
    private String skillName;
    @NotNull(message = "Category ID is required")
    private Long categoryId;
}
