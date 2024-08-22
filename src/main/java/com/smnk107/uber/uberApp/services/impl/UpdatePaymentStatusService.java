package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.entities.Payment;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.enums.PaymentStatus;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePaymentStatusService {

    private  final PaymentRepository paymentRepository;


    public void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(()->new ResourceNotFoundException("Payment not found with payment id :"+paymentId));

        payment.setPaymentStatus(paymentStatus);
        paymentRepository.save(payment);
    }

    public Payment getPaymentForRide(Ride ride) {
        return paymentRepository.findByRide(ride).orElseThrow(()->new ResourceNotFoundException("No payment found for the ride !"));
    }
}
