package com.Skill.Marketplace.SM.DTO.CategoryDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryDTO {
    @NotBlank(message = "Category name is required")
    private String categoryName;
}
