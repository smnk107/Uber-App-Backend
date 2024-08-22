package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.entities.Driver;
import com.smnk107.uber.uberApp.entities.Payment;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.enums.PaymentStatus;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.PaymentRepository;
import com.smnk107.uber.uberApp.services.PaymentService;
import com.smnk107.uber.uberApp.strategies.PaymentStrategyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentStrategyManager paymentStrategyManager;
    @Override
    public void processPayment(Ride ride) {
        Payment payment = paymentRepository.findByRide(ride)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for ride with id: "+ride.getId()));
        paymentStrategyManager.paymentStrategy(payment.getPaymentMethod()).processPayment(ride);
    }

    @Override
    public Payment createNewPayment(Ride ride) {

        Payment payment = Payment.builder()
                .ride(ride)
                .amount(ride.getFare())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentMethod(ride.getPaymentMethod())
                .build();

        return paymentRepository.save(payment);

    }

    @Override
    public void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found with payment id :"+paymentId));

        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentForRide(Ride ride) {
        return paymentRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("No payment found for the ride !"));
    }


}
