package com.Skill.Marketplace.SM.Services;

import com.Skill.Marketplace.SM.DTO.TrustDTO.PrivateTrustDTO;
import com.Skill.Marketplace.SM.DTO.TrustDTO.PublicTrustDTO;
import com.Skill.Marketplace.SM.Entities.Order;
import com.Skill.Marketplace.SM.Entities.OrderStatus;
import com.Skill.Marketplace.SM.Entities.PaymentStatus;
import com.Skill.Marketplace.SM.Repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TrustService {

    @Autowired
    private OrderRepo orderRepo;


    //helper class for calculating badge based on completion rate and refunds
    private String deriveBadge(double completionRate, long refunds, long total) {
        if (total == 0) return "NEW";
        if (completionRate >= 80 && refunds == 0) return "TRUSTED";
        if (completionRate >= 50) return "NEUTRAL";
        return "RISKY";
    }

    private double round(double value) {
        return Math.round(value * 10.0) / 10.0;
    }


    public PublicTrustDTO getProviderTrustPublic(String username) {
        List<Order> orders = orderRepo.findByProvider_Username(username);

        long accepted = orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.PENDING)
                .count();

        long completed = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .count();

        long cancelled = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.CANCELLED)
                .count();

        long refunds = orders.stream()
                .filter(o -> o.getMockPaymentStatus() == PaymentStatus.REFUNDED)
                .count();

        double completionRate = accepted == 0 ? 0.0 : (double) (completed * 100) / accepted;

        String badge = deriveBadge(completionRate, refunds, orders.size());

        return new PublicTrustDTO(
                completed,
                cancelled,
                refunds,
                completionRate,
                badge
        );
    }

    public PublicTrustDTO getConsumerTrustPublic(String username) {
        List<Order> orders = orderRepo.findByConsumer_Username(username);

        long completed = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .count();

        long cancelled = orders.stream()
                .filter(o -> o.getStatus() == OrderStatus.CANCELLED)
                .count();

        long refunds = orders.stream()
                .filter(o -> o.getMockPaymentStatus() == PaymentStatus.REFUNDED)
                .count();

        double completionRate = orders.isEmpty() ? 0.0 : (double) (completed * 100) / orders.size();

        String badge = deriveBadge(completionRate, refunds, orders.size());

        return new PublicTrustDTO(
                completed,
                cancelled,
                refunds,
                completionRate,
                badge
        );
    }

    public PrivateTrustDTO getMyTrust(String username) {
        List<Order> providerOrders = orderRepo.findByProvider_Username(username);
        List<Order> consumerOrders = orderRepo.findByConsumer_Username(username);

        long jobsAsProvider = providerOrders.size();

        long completedAsProvider = providerOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .count();

        double providerCompletionRate =
                jobsAsProvider == 0 ? 0.0
                        : (completedAsProvider * 100.0) / jobsAsProvider;

        long jobsAsConsumer = consumerOrders.size();

        long refundsAsConsumer = consumerOrders.stream()
                .filter(o -> o.getMockPaymentStatus() == PaymentStatus.REFUNDED)
                .count();

        double consumerRefundRate =
                jobsAsConsumer == 0 ? 0.0
                        : (refundsAsConsumer * 100.0) / jobsAsConsumer;

        String trustBadge = deriveBadge(providerCompletionRate, refundsAsConsumer, jobsAsProvider);

        return new PrivateTrustDTO(
                jobsAsProvider,
                completedAsProvider,
                jobsAsConsumer,
                refundsAsConsumer,
                round(providerCompletionRate),
                round(consumerRefundRate),
                trustBadge
        );

    }
}
