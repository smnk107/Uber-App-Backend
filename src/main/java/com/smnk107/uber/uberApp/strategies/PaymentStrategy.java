package com.smnk107.uber.uberApp.strategies;

import com.smnk107.uber.uberApp.entities.Payment;
import com.smnk107.uber.uberApp.entities.Ride;

public interface PaymentStrategy {

    static final Double PLATFORM_COMISSION = 0.3;
    void processPayment(Ride ride);
}
