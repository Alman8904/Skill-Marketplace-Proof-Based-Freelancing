package com.Skill.Marketplace.SM.Mapper;
import com.Skill.Marketplace.SM.DTO.categoryDTO.CategoryResponseDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponseDTO toResponse(Category category){
        CategoryResponseDTO response = new CategoryResponseDTO();

        response.setId(category.getCategoryId());
        response.setCategoryName(category.getCategoryName());

        return response;
    }
}
