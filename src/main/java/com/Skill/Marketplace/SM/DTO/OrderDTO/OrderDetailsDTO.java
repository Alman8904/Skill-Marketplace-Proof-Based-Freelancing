package com.Skill.Marketplace.SM.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
    private Long orderId;
    private String consumerName;
    private String providerName;
    private String skillName;
    private String description;
    private double agreedPrice;
    private String status;
    private String createdAt;
    private String completedAt;
}
