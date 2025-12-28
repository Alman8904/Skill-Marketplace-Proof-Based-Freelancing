package com.Skill.Marketplace.SM.Entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserSkills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSkillId;
    private String skillLevel;
    private double rate;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name="skill_id")
    private Skills skills;
}
