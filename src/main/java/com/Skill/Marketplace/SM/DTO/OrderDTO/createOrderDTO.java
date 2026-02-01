package com.Skill.Marketplace.SM.DTO.OrderDTO;
import lombok.Data;

@Data
public class createOrderDTO {
    private Long providerId;
    private Long skillId;
    private String description;
}
