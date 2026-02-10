package com.Skill.Marketplace.SM.DTO.OrderDTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class DeliverWorkDTO {

    @NotNull(message = "Order ID cannot be null")
    private Long orderId;

    @NotNull(message = "Delivery notes cannot be null")
    private String deliveryNotes;

    private String deliveryUrl;
}
