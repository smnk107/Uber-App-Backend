package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.entities.Wallet;
import com.smnk107.uber.uberApp.entities.enums.TransactionMethod;

public interface WalletService {
    Wallet addMoneyToWallet(User user, Double money, String trnId, Ride ride,
                            TransactionMethod transactionMethod);
    void clearWallet();

    Wallet deductMoneyFromWallet(User user, Double money, String trnId, Ride ride,
                                 TransactionMethod transactionMethod);

    Wallet findWalletById(Long walletId);

    Wallet createNewWallet(User user);

    Wallet findByUser(User user);
}
