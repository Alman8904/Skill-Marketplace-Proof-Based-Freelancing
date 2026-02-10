package com.Skill.Marketplace.SM.DTO.OrderDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateOrderDTO {

    @NotNull(message = "Provider ID is required")
    private Long providerId;
    @NotNull(message = "Skill ID is required")
    private Long skillId;
    @NotBlank(message = "Description is required")
    @Size(min = 10, max = 500)
    private String description;
    @NotNull(message = "Estimated Hours is required")
    private Integer estimatedHours;

}
