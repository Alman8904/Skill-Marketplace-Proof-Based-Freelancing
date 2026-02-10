package com.Skill.Marketplace.SM.DTO.UserSkillDTO;

import com.Skill.Marketplace.SM.Entities.ServiceMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserSkillDTO {

    @NotBlank
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotNull
    @Size(min = 0, message = "Rate must be non-negative")
    private double rate;

    @NotNull
    @Size(min = 0, message = "Experience must be non-negative")
    private int experience;

    @NotNull(message = "Service mode is required")
    private ServiceMode serviceMode;
}
