package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.categoryDTO.CategoryResponseDTO;
import com.Skill.Marketplace.SM.DTO.categoryDTO.CreateCategoryDTO;
import com.Skill.Marketplace.SM.DTO.categoryDTO.UpdateCategoryDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import com.Skill.Marketplace.SM.Mapper.CategoryMapper;
import com.Skill.Marketplace.SM.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryMapper categoryMapper;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CreateCategoryDTO dto){
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.create(dto)) );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id , @RequestBody UpdateCategoryDTO dto){
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.update(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryMapper.toResponse(categoryService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories(){
        List<Category> categories = categoryService.getAll();
        List<CategoryResponseDTO> response = categories.stream()
                .map(categoryMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id){
         categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
