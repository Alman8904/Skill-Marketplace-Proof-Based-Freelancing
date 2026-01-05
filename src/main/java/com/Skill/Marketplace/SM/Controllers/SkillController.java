package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.skillDTO.CreateSkillDTO;
import com.Skill.Marketplace.SM.DTO.skillDTO.UpdateSkillDTO;
import com.Skill.Marketplace.SM.Entities.Skills;
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

    @PostMapping
    public ResponseEntity<Skills> createSkill(@RequestBody CreateSkillDTO dto){
        return ResponseEntity.ok(skillService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Skills> updateSkill(@PathVariable Long id , @RequestBody UpdateSkillDTO dto){
        return ResponseEntity.ok(skillService.update(id, dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Skills> getSkillById(@PathVariable Long id){
        return ResponseEntity.ok(skillService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Skills>> getAllSkills(){
        return ResponseEntity.ok(skillService.getAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkillById(@PathVariable Long id){
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
