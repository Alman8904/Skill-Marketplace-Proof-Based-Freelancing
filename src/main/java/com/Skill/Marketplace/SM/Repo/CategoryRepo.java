package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

}
