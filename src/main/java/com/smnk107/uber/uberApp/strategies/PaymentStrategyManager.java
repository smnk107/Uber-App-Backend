package com.smnk107.uber.uberApp.strategies;


import com.smnk107.uber.uberApp.entities.enums.PaymentMethod;
import com.smnk107.uber.uberApp.strategies.impl.CashPaymentStrategy;
import com.smnk107.uber.uberApp.strategies.impl.WalletPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentStrategyManager {

    private final CashPaymentStrategy cashPaymentStrategy;
    private final WalletPaymentStrategy walletPaymentStrategy;

    public PaymentStrategy paymentStrategy(PaymentMethod paymentMethod)
    {
        return switch (paymentMethod)
        {
            case WALLET -> walletPaymentStrategy;
            case CASH -> cashPaymentStrategy;
        };
    }
}
