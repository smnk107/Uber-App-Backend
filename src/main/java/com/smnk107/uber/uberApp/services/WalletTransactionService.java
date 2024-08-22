package com.smnk107.uber.uberApp.services;

import com.smnk107.uber.uberApp.dto.WalletTransactionDTO;
import com.smnk107.uber.uberApp.entities.WalletTransaction;

public interface WalletTransactionService {
    void createNewWalletTransaction(WalletTransaction walletTransaction);
}
