package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.entities.Payment;
import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride);
    Payment createNewPayment(Ride ride);

    void updatePaymentStatus(Long paymentId, PaymentStatus paymentStatus);

    Payment getPaymentForRide(Ride ride);

}
