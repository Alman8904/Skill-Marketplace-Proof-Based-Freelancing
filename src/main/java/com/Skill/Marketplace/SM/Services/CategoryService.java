package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.DTO.CategoryDTO.CreateCategoryDTO;
import com.Skill.Marketplace.SM.DTO.CategoryDTO.UpdateCategoryDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category create(CreateCategoryDTO dto) {

        if (dto.getCategoryName() == null || dto.getCategoryName().isEmpty()) {
            throw new BadRequestException("Category name cannot be empty");
        }

        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());

        return categoryRepo.save(category);
    }

    public Category update(Long id, UpdateCategoryDTO dto) {
        Category existingCategory = categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (dto.getCategoryName() == null || dto.getCategoryName().isEmpty()) {
            throw new BadRequestException("Category name cannot be empty");
        }

        existingCategory.setCategoryName(dto.getCategoryName());
        return categoryRepo.save(existingCategory);

    }

    public Category getById(Long id) {
        return categoryRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public List<Category> getAll() {
        return categoryRepo.findAll();
    }

    public void delete(Long id) {
        categoryRepo.deleteById(id);

        if (!categoryRepo.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
    }
}
