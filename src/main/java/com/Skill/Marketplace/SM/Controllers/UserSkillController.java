package com.Skill.Marketplace.SM.Controllers;

import com.Skill.Marketplace.SM.DTO.SearchDTO.SearchResultDTO;
import com.Skill.Marketplace.SM.DTO.UserSkillDTO.AssignSkillDTO;
import com.Skill.Marketplace.SM.DTO.UserSkillDTO.UpdateUserSkillDTO;
import com.Skill.Marketplace.SM.Entities.ServiceMode;
import com.Skill.Marketplace.SM.Entities.UserSkill;
import com.Skill.Marketplace.SM.Services.UserSkillService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-skills")
public class UserSkillController {

    @Autowired
    private UserSkillService userSkillService;

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/assign")
    public ResponseEntity<?> assignSkillToUser(@Valid @RequestBody AssignSkillDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userSkillService.assignSkills(username, dto);
        return ResponseEntity.ok("Skill(s) assigned to user");
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/all-userSkills")
    public ResponseEntity<?> getSkillsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(userSkillService.getSkillsByUser(username));
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PutMapping("/update/{userSkillId}")
    public ResponseEntity<?> updateUserSkill(@PathVariable Long userSkillId, @Valid @RequestBody UpdateUserSkillDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userSkillService.updateUserSkill(userSkillId, username, dto);
        return ResponseEntity.ok("UserSkill updated successfully");
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @DeleteMapping("/deactivate/{userSkillId}")
    public ResponseEntity<?> deactivateUserSkill(@PathVariable Long userSkillId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userSkillService.deactivateUserSkill(userSkillId, username);
        return ResponseEntity.ok("UserSkill deactivated successfully");
    }


    @PreAuthorize("hasAnyRole('CONSUMER','PROVIDER')")
    @GetMapping("/search")
    public ResponseEntity<?> searchProviders(
            @RequestParam String skill,
            @RequestParam(required = false) Double minRate,
            @RequestParam(required = false) Double maxRate,
            @RequestParam(required = false) ServiceMode serviceMode,
            @RequestParam(required = false) Integer minExperience,
            @PageableDefault(size = 10, sort = "rate") Pageable pageable
    ) {
        Page<UserSkill> page = userSkillService.searchProvidersBySkill(
                skill, minRate, maxRate, serviceMode, minExperience, pageable
        );

        return ResponseEntity.ok(page.map(us -> new SearchResultDTO(
                us.getUser().getId(),
                us.getUser().getUsername(),
                us.getSkill().getSkillName(),
                us.getRate(),
                us.getDescription(),
                us.getExperience(),
                us.getServiceMode().name()
        )));
    }
}

