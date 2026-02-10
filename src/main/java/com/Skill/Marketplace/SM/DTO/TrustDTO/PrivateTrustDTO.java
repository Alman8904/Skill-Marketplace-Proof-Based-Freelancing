package com.Skill.Marketplace.SM.DTO.TrustDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PrivateTrustDTO {

    private long jobsAsProvider;
    private long completedAsProvider;

    private long jobsAsConsumer;
    private long refundsAsConsumer;

    private double providerCompletionRate;
    private double consumerRefundRate;

    private String trustBadge;
}
