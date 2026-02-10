package com.Skill.Marketplace.SM.DTO.CategoryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryResponseDTO {

    private Long id;
    private String categoryName;
}
