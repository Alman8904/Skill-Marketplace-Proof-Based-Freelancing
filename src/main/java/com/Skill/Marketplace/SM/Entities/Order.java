package com.Skill.Marketplace.SM.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "consumer_id", nullable = false)
    private UserModel consumer;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provider_id", nullable = false)
    private UserModel provider;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private double agreedPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    private Integer estimatedHours; // Consumer
    private LocalDateTime deadline; // Provider


    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
    @Column(length = 100)
    private String deliveryNotes;
    @Column(length = 500)
    private String deliveryUrl;
    private LocalDateTime deliveredAt;
    private LocalDateTime approvedAt;
    @Column(length = 100)
    private String mockPaymentId;
    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private PaymentStatus mockPaymentStatus = PaymentStatus.PENDING;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) {
            status = OrderStatus.PENDING;
        }
    }


}
