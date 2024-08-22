package com.smnk107.uber.uberApp.strategies.impl;

import com.smnk107.uber.uberApp.entities.*;
import com.smnk107.uber.uberApp.entities.enums.PaymentStatus;
import com.smnk107.uber.uberApp.entities.enums.TransactionMethod;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.WalletRepository;
import com.smnk107.uber.uberApp.services.PaymentService;
import com.smnk107.uber.uberApp.services.WalletService;
import com.smnk107.uber.uberApp.services.WalletTransactionService;
import com.smnk107.uber.uberApp.services.impl.UpdatePaymentStatusService;
import com.smnk107.uber.uberApp.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class WalletPaymentStrategy implements PaymentStrategy {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final WalletService walletService;
    //private final PaymentService paymentService;
    private final UpdatePaymentStatusService updatePaymentStatusService;


    @Override
    public void processPayment(Ride ride) {

        Payment payment = updatePaymentStatusService.getPaymentForRide(ride);

        Driver driver = payment.getRide().getDriver();
        Rider rider = payment.getRide().getRider();

        Double rideFare = payment.getAmount();
        Double platformComission = rideFare*PLATFORM_COMISSION;

        updatePaymentStatusService.updatePaymentStatus(payment.getId(), PaymentStatus.CONFIRMED);

        walletService.deductMoneyFromWallet(rider.getUser(),rideFare,null,payment.getRide(),TransactionMethod.RIDE);
        walletService.addMoneyToWallet(driver.getUser(), rideFare-platformComission,null, payment.getRide(), TransactionMethod.RIDE);

    }
}
