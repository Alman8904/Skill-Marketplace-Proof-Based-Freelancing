package com.Skill.Marketplace.SM.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSkillId;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false)
    private double rate;

    @Column(nullable = false)
    private int experience;

    @Column(nullable = false)
    private boolean isActive = true;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceMode serviceMode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
}
