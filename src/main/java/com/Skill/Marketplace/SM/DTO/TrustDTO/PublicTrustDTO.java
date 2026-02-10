package com.Skill.Marketplace.SM.DTO.TrustDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PublicTrustDTO {

    private long completedJobs;
    private long cancelledJobs;
    private long refundCount;
    private double completionRate;
    private String trustBadge;
}
