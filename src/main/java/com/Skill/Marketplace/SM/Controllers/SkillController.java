package com.Skill.Marketplace.SM.Controllers;

import com.Skill.Marketplace.SM.DTO.CategoryDTO.CategoryResponseDTO;
import com.Skill.Marketplace.SM.DTO.SkillDTO.CreateSkillDTO;
import com.Skill.Marketplace.SM.DTO.SkillDTO.SkillResponseDTO;
import com.Skill.Marketplace.SM.DTO.SkillDTO.UpdateSkillDTO;
import com.Skill.Marketplace.SM.Entities.Skill;
import com.Skill.Marketplace.SM.Services.SkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createSkill(@Valid @RequestBody CreateSkillDTO dto) {
        Skill savedSkill = skillService.create(dto);
        return ResponseEntity.ok(
                new SkillResponseDTO(
                        savedSkill.getId(),
                        savedSkill.getSkillName(),
                        savedSkill.getCategory() != null ? new CategoryResponseDTO(
                                savedSkill.getCategory().getCategoryId(),
                                savedSkill.getCategory().getCategoryName()
                        ) : null
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSkillById(@PathVariable Long id) {
        Skill skill = skillService.getById(id);
        return ResponseEntity.ok(
                new SkillResponseDTO(
                        skill.getId(),
                        skill.getSkillName(),
                        skill.getCategory() != null ? new CategoryResponseDTO(
                                skill.getCategory().getCategoryId(),
                                skill.getCategory().getCategoryName()
                        ) : null
                )
        );
    }

    @GetMapping
    public ResponseEntity<?> getAllSkills(Pageable pageable) {
        Page<Skill> page = skillService.getAll(pageable);

        Page<SkillResponseDTO> dtoPage = page.map(
                skill -> new SkillResponseDTO(
                        skill.getId(),
                        skill.getSkillName(),
                        skill.getCategory() != null ? new CategoryResponseDTO(
                                skill.getCategory().getCategoryId(),
                                skill.getCategory().getCategoryName()
                        ) : null
                )
        );
        return ResponseEntity.ok(dtoPage);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSkill(@PathVariable Long id, @Valid @RequestBody UpdateSkillDTO dto) {
        skillService.update(id, dto);
        return ResponseEntity.ok(
                new SkillResponseDTO(
                        id,
                        dto.getSkillName(),
                        null
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSkillById(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
