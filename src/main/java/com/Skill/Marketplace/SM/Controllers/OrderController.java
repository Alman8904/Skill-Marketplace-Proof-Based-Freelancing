package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.OrderDTO.createOrderDTO;
import com.Skill.Marketplace.SM.DTO.OrderDTO.orderDetailsDTO;
import com.Skill.Marketplace.SM.Entities.Order;
import com.Skill.Marketplace.SM.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@RequestBody createOrderDTO orderDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Order order =orderService.placeOrder(username, orderDTO);

        return ResponseEntity.ok(
                new orderDetailsDTO(
                        order.getOrderId(),
                        order.getConsumer().getUsername(),
                        order.getProvider().getUsername(),
                        order.getSkill().getSkillName(),
                        order.getDescription(),
                        order.getAgreedPrice(),
                        order.getStatus().name(),
                        order.getCreatedAt().toString(),
                        order.getCompletedAt() != null ? order.getCompletedAt().toString() : null
                )
        );
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/accept")
    public ResponseEntity<?> acceptOrder(@RequestParam Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.acceptOrder(orderId, username);
        return ResponseEntity.noContent().build();
    }


    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelOrder(@RequestParam Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        orderService.cancelOrder(orderId, username);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/complete")
    public ResponseEntity<?> completeOrder(@RequestParam Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        orderService.completeOrder(orderId, username);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                orderService.getMyOrders(username)
                        .stream()
                        .map(order -> new orderDetailsDTO(
                                order.getOrderId(),
                                order.getConsumer().getUsername(),
                                order.getProvider().getUsername(),
                                order.getSkill().getSkillName(),
                                order.getDescription(),
                                order.getAgreedPrice(),
                                order.getStatus().name(),
                                order.getCreatedAt().toString(),
                                order.getCompletedAt() != null
                                        ? order.getCompletedAt().toString()
                                        : null
                        ))
                        .toList()
        );
    }


    @PreAuthorize("hasRole('PROVIDER')")
    @GetMapping("/received-orders")
    public ResponseEntity<?> getReceivedOrders() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                orderService.getReceivedOrders(username)
                        .stream()
                        .map(order -> new orderDetailsDTO(
                                order.getOrderId(),
                                order.getConsumer().getUsername(),
                                order.getProvider().getUsername(),
                                order.getSkill().getSkillName(),
                                order.getDescription(),
                                order.getAgreedPrice(),
                                order.getStatus().name(),
                                order.getCreatedAt().toString(),
                                order.getCompletedAt() != null
                                        ? order.getCompletedAt().toString()
                                        : null
                        ))
                        .toList()
        );
    }
}
