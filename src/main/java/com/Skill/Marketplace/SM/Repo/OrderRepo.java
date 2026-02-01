package com.Skill.Marketplace.SM.Repo;
import com.Skill.Marketplace.SM.Entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByConsumer_Username(String username);
    List<Order> findByProvider_Username(String username);

}
