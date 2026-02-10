package com.Skill.Marketplace.SM.Controllers;

import com.Skill.Marketplace.SM.DTO.OrderDTO.CreateOrderDTO;
import com.Skill.Marketplace.SM.DTO.OrderDTO.DeliverWorkDTO;
import com.Skill.Marketplace.SM.DTO.OrderDTO.OrderDetailsDTO;
import com.Skill.Marketplace.SM.Entities.Order;
import com.Skill.Marketplace.SM.Entities.PaymentStatus;
import com.Skill.Marketplace.SM.Exception.ConflictException;
import com.Skill.Marketplace.SM.Repo.OrderRepo;
import com.Skill.Marketplace.SM.Services.MockPaymentService;
import com.Skill.Marketplace.SM.Services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private MockPaymentService mockPaymentService;

    @PreAuthorize("hasRole('CONSUMER') or hasRole('PROVIDER')")
    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(@Valid @RequestBody CreateOrderDTO orderDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Order order = orderService.placeOrder(username, orderDTO);

        return ResponseEntity.ok(
                new OrderDetailsDTO(
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
    public ResponseEntity<?> acceptOrder(@RequestParam Long orderId, @RequestParam String deadline) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Order order = orderRepo.findById(orderId).orElseThrow();

        if (order.getMockPaymentStatus() != PaymentStatus.AUTHORIZED)
            throw new ConflictException("Payment not authorized yet");


        LocalDateTime date = LocalDateTime.parse(deadline);
        orderService.acceptOrder(orderId, username, date);
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
    @PostMapping("/deliver")
    public ResponseEntity<?> deliverOrder(@RequestParam Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        orderService.deliverOrder(orderId, username);

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/start-work")
    public ResponseEntity<?> startWork(@RequestParam Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.startWork(orderId, username);

        return ResponseEntity.ok(
                Map.of(
                        "message", "Work started on order ",
                        "orderId", orderId
                )
        );
    }

    @PreAuthorize("hasRole('PROVIDER')")
    @PostMapping("/deliver-work")
    public ResponseEntity<?> deliverWork(@Valid @RequestBody DeliverWorkDTO deliverDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        orderService.deliverWork(
                deliverDTO.getOrderId(),
                username,
                deliverDTO.getDeliveryNotes(),
                deliverDTO.getDeliveryUrl()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message", "Work delivered for order ",
                        "orderId", deliverDTO.getOrderId()
                )
        );
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/approve-delivery")
    public ResponseEntity<?> approveDelivery(@RequestParam Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        orderService.approveDelivery(orderId, username);

        Order order = orderRepo.findById(orderId).orElseThrow();

        try {
            mockPaymentService.capturePayment(orderId);
        } catch (Exception e) {
            System.err.println("Failed to capture payment for order " + e.getMessage());
        }

        return ResponseEntity.ok(
                Map.of(
                        "message", "Delivery approved for order ",
                        "orderId", orderId,
                        "amountReleased", order.getAgreedPrice()
                )
        );
    }

    @PreAuthorize("hasRole('CONSUMER')")
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(
                orderService.getMyOrders(username)
                        .stream()
                        .map(order -> new OrderDetailsDTO(
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
                        .map(order -> new OrderDetailsDTO(
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
