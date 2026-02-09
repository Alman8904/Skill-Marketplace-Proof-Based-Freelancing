package com.Skill.Marketplace.SM.Services;
import com.Skill.Marketplace.SM.Entities.Order;
import com.Skill.Marketplace.SM.Entities.PaymentStatus;
import com.Skill.Marketplace.SM.Entities.UserModel;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.OrderRepo;
import com.Skill.Marketplace.SM.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class MockPaymentService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrderRepo orderRepo;


    @Transactional
    public void addFundsToWallet(String username, Double amount) {
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setWalletBalance(user.getWalletBalance() + amount);
        userRepo.save(user);
    }


     // Authorize payment (hold funds in escrow)

    @Transactional
    public String authorizePayment(Long orderId, String username, Double amount) {
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (user.getWalletBalance() < amount) {
            throw new BadRequestException(
                    String.format("Insufficient balance. Required: %.2f, Available: %.2f",
                            amount, user.getWalletBalance())
            );
        }

        user.setWalletBalance(user.getWalletBalance() - amount);
        userRepo.save(user);

        // Generate mock payment ID
        String paymentId = "PAY_" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Update order with payment details
        order.setMockPaymentId(paymentId);
        order.setMockPaymentStatus(PaymentStatus.AUTHORIZED);
        orderRepo.save(order);
        return paymentId;
    }


     //Capture payment (release funds to provider)

    @Transactional
    public void capturePayment(Long orderId) {


        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getMockPaymentStatus() != PaymentStatus.AUTHORIZED) {
            throw new BadRequestException("Payment must be in AUTHORIZED status to capture");
        }

        // Add money to provider's wallet
        UserModel provider = order.getProvider();
        provider.setWalletBalance(provider.getWalletBalance() + order.getAgreedPrice());
        userRepo.save(provider);

        // Update payment status
        order.setMockPaymentStatus(PaymentStatus.CAPTURED);
        orderRepo.save(order);


    }


     // Refund payment (return funds to consumer)
    @Transactional
    public void refundPayment(Long orderId) {


        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getMockPaymentStatus() != PaymentStatus.AUTHORIZED) {
            throw new BadRequestException("Can only refund AUTHORIZED payments");
        }

        // Return money to consumer's wallet
        UserModel consumer = order.getConsumer();
        consumer.setWalletBalance(consumer.getWalletBalance() + order.getAgreedPrice());
        userRepo.save(consumer);

        // Update payment status
        order.setMockPaymentStatus(PaymentStatus.REFUNDED);
        orderRepo.save(order);

    }


     // Get wallet balance for a user

    public Double getWalletBalance(String username) {
        UserModel user = userRepo.getUserByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getWalletBalance();
    }
}