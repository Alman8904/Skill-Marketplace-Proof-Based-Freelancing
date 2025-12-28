package com.Skill.Marketplace.SM.Entities;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String FirstName;
    private String LastName;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @OneToMany(mappedBy = "user")
    private List<UserSkills> userSkills;

}
