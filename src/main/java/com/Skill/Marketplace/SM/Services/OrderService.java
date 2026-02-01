package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.DTO.OrderDTO.createOrderDTO;
import com.Skill.Marketplace.SM.Entities.*;
import com.Skill.Marketplace.SM.Repo.OrderRepo;
import com.Skill.Marketplace.SM.Repo.SkillsRepo;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import com.Skill.Marketplace.SM.Repo.UserSkillRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SkillsRepo skillsRepo;

    @Autowired
    private UserSkillRepo userSkillRepo;

    @Autowired
    private OrderRepo orderRepo;

    public Order placeOrder(String username , createOrderDTO orderDTO) {
        UserModel consumer = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("Consumer not found"));

        if(consumer.getUserType() != UserType.CONSUMER) {
            throw new RuntimeException("Only consumers can place orders");
        }

        UserModel provider = userRepo.findById(orderDTO.getProviderId())
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        if (provider.getUserType() == UserType.CONSUMER) {
            throw new RuntimeException("This user does not offer services");
        }

        Skill skill =  skillsRepo.findById(orderDTO.getSkillId())
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        UserSkill listing = userSkillRepo.findByUserAndSkill(provider , skill)
                .orElseThrow(() -> new RuntimeException("Provider does not offer this skill"));


        Order order = new Order();
        order.setConsumer(consumer);
        order.setProvider(provider);
        order.setSkill(skill);
        order.setDescription(orderDTO.getDescription());
        order.setAgreedPrice(listing.getRate());
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepo.save(order);
    }

    @Transactional
    public void acceptOrder(Long orderId, String username) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (!order.getProvider().getUsername().equals(username))
            throw new RuntimeException("Not authorized");

        if(order.getStatus() != OrderStatus.PENDING)
            throw new RuntimeException("Order already processed");

        order.setStatus(OrderStatus.ACCEPTED);
    }

    @Transactional
    public void completeOrder(Long orderId, String username) {
        Order order = orderRepo.findById(orderId).orElseThrow();

        if (!order.getProvider().getUsername().equals(username))
            throw new RuntimeException("Not authorized");

        if(order.getStatus() != OrderStatus.ACCEPTED)
            throw new RuntimeException("Order must be accepted first");

        order.setStatus(OrderStatus.COMPLETED);
        order.setCompletedAt(LocalDateTime.now());

    }

    @Transactional
    public void cancelOrder(Long orderId, String username) {
        Order order = orderRepo.findById(orderId).orElseThrow();

        if (!order.getConsumer().getUsername().equals(username))
            throw new RuntimeException("Not authorized");

        if (order.getStatus() != OrderStatus.PENDING)
            throw new RuntimeException("Cannot cancel now");

        order.setStatus(OrderStatus.CANCELLED);

    }

    public List<Order> getMyOrders(String username) {
        return orderRepo.findByConsumer_Username(username);
    }

    public List<Order> getReceivedOrders(String username) {
        return orderRepo.findByProvider_Username(username);
    }



}
