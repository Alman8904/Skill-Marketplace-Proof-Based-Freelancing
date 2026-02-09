package com.Skill.Marketplace.SM.Controllers;
import com.Skill.Marketplace.SM.DTO.paymentDTO.AddFundsDTO;
import com.Skill.Marketplace.SM.DTO.paymentDTO.PaymentInitiateDTO;
import com.Skill.Marketplace.SM.Entities.Order;
import com.Skill.Marketplace.SM.Entities.OrderStatus;
import com.Skill.Marketplace.SM.Entities.PaymentStatus;
import com.Skill.Marketplace.SM.Exception.BadRequestException;
import com.Skill.Marketplace.SM.Exception.ForbiddenException;
import com.Skill.Marketplace.SM.Exception.ResourceNotFoundException;
import com.Skill.Marketplace.SM.Repo.OrderRepo;
import com.Skill.Marketplace.SM.Services.MockPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private MockPaymentService mockPaymentService;

    @Autowired
    private OrderRepo orderRepo;

     // Add funds to wallet
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-funds")
    public ResponseEntity<?> addFunds(@Valid @RequestBody AddFundsDTO addFundsDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        mockPaymentService.addFundsToWallet(username, addFundsDTO.getAmount());

        return ResponseEntity.ok(Map.of(
                "message", "Funds added successfully",
                "amount", addFundsDTO.getAmount(),
                "walletBalance", mockPaymentService.getWalletBalance(username)
        ));

    }


     // Get wallet balance
    @GetMapping("/wallet-balance")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getWalletBalance() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Double balance = mockPaymentService.getWalletBalance(username);

        return ResponseEntity.ok(Map.of(
                "username", username,
                "walletBalance", balance
        ));
    }


     //Authorize payment for an order (holds funds in escrow)
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/authorize")
    @Transactional
    public ResponseEntity<?> authorizePayment(@Valid @RequestBody PaymentInitiateDTO paymentDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch order
        Order order = orderRepo.findById(paymentDTO.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Verify ownership
        if (!order.getConsumer().getUsername().equals(username)) {
            throw new ForbiddenException("Not authorized to pay for this order");
        }

        // Verify payment status
        if (order.getMockPaymentStatus() != PaymentStatus.PENDING) {
            throw new BadRequestException("Payment already initiated");
        }

        // Verify amount
        if (!paymentDTO.getAmount().equals(order.getAgreedPrice())) {
            throw new BadRequestException("Payment amount does not match order amount");
        }

        // Authorize payment (deduct from consumer wallet, hold in escrow)
        String paymentId = mockPaymentService.authorizePayment(
                order.getOrderId(),
                username,
                paymentDTO.getAmount()
        );

        return ResponseEntity.ok(Map.of(
                "message", "Payment authorized successfully",
                "orderId", order.getOrderId(),
                "paymentId", paymentId,
                "amount", paymentDTO.getAmount(),
                "walletBalance", mockPaymentService.getWalletBalance(username)
        ));
    }


     // Refund payment if order cancelled
    @PreAuthorize("hasRole('CONSUMER')")
    @PostMapping("/refund")
    @Transactional
    public ResponseEntity<?> refundPayment(@RequestParam Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Fetch order
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Verify ownership
        if (!order.getConsumer().getUsername().equals(username)) {
            throw new ForbiddenException("Not authorized");
        }

        // Verify payment exists and is authorized
        if (order.getMockPaymentStatus() != PaymentStatus.AUTHORIZED) {
            throw new BadRequestException("No authorized payment found for this order");
        }

        // Refund the payment
        mockPaymentService.refundPayment(orderId);

        // Update order status
        order.setStatus(OrderStatus.CANCELLED);
        orderRepo.save(order);

        return ResponseEntity.ok(Map.of(
                "message", "Payment refunded successfully",
                "orderId", order.getOrderId(),
                "amount", order.getAgreedPrice(),
                "walletBalance", mockPaymentService.getWalletBalance(username)
        ));
    }
}