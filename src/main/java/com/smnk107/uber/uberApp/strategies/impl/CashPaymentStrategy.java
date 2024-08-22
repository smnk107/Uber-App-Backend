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
import org.hibernate.annotations.Fetch;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy {

    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final WalletService walletService;

    //private final PaymentService paymentService;
    private final UpdatePaymentStatusService updatePaymentStatusService;

    @Override
    public void processPayment(Ride ride) {

        Payment payment = updatePaymentStatusService.getPaymentForRide(ride);
        Driver driver = payment.getRide().getDriver();

        Double platformComission = payment.getAmount()*PLATFORM_COMISSION;
        updatePaymentStatusService.updatePaymentStatus(payment.getId(), PaymentStatus.CONFIRMED);

        walletService.deductMoneyFromWallet(driver.getUser(), platformComission,null,
                                        payment.getRide(),TransactionMethod.RIDE);

    }
}
