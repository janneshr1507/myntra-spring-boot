package com.jannesh.service;

import com.jannesh.dto.PaymentDTO;
import com.jannesh.entity.Cart;
import com.jannesh.entity.Payment;
import com.jannesh.repository.PaymentRepository;
import com.jannesh.util.enums.CartStatus;
import com.jannesh.util.enums.PaymentStatus;
import com.jannesh.util.mapper.PaymentMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepo;
    private final CartService cartService;
    private final PaymentMapper mapper;

    @Transactional
    public PaymentDTO processPayment(UUID cartId) {
        Cart cart = cartService.fetchCartByCartId(cartId);

        Payment payment = new Payment();
        payment.setCartId(cartId);
        payment.setAmount(cart.getTotalAmount());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);

        cart.setCartStatus(CartStatus.CLOSED);
        cartService.updateCartDetails(cart);

        return mapper.toDTO(paymentRepo.save(payment));
    }
}
