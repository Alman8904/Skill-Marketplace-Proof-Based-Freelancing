package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.categoryDTO.CategoryResponseDTO;
import com.Skill.Marketplace.SM.DTO.skillDTO.CreateSkillDTO;
import com.Skill.Marketplace.SM.DTO.skillDTO.SkillResponseDTO;
import com.Skill.Marketplace.SM.DTO.skillDTO.UpdateSkillDTO;
import com.Skill.Marketplace.SM.Entities.Category;
import com.Skill.Marketplace.SM.Entities.Skills;
import com.Skill.Marketplace.SM.Mapper.SkillMapper;
import com.Skill.Marketplace.SM.Services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/skills")
public class SkillController {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillMapper skillMapper;

    @PostMapping
    public ResponseEntity<SkillResponseDTO> createSkill(@RequestBody CreateSkillDTO dto){
        return ResponseEntity.ok(skillMapper.toResponse(skillService.create(dto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> updateSkill(@PathVariable Long id , @RequestBody UpdateSkillDTO dto){
        return ResponseEntity.ok(skillMapper.toResponse(skillService.update(id, dto)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponseDTO> getSkillById(@PathVariable Long id){
        return ResponseEntity.ok(skillMapper.toResponse(skillService.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<SkillResponseDTO>> getAllSkills(){
        List<Skills> categories = skillService.getAll();
        List<SkillResponseDTO> response = categories.stream()
                .map(skillMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkillById(@PathVariable Long id){
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
