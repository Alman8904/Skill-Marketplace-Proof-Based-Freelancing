package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.Skills;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepo extends JpaRepository<Skills,Long> {
}
