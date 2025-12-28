package com.Skill.Marketplace.SM.Entities;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "skills")
    private List<UserSkills> userSkills;

}
