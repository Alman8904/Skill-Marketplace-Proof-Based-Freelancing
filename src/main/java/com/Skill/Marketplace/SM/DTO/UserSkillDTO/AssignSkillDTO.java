package com.Skill.Marketplace.SM.DTO.UserSkillDTO;

import com.Skill.Marketplace.SM.Entities.ServiceMode;
import lombok.Data;

import java.util.List;

@Data
public class AssignSkillDTO {

    private List<SkillData> skills;

    @Data
    public static class SkillData {
        private Long skillId;
        private String description;
        private double rate;
        private int experience;
        private ServiceMode serviceMode;
    }
}
