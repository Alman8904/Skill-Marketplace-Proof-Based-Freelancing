package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsRepo extends JpaRepository<Skill,Long> {
    Page<Skill> findAll(Pageable pageable);
}
