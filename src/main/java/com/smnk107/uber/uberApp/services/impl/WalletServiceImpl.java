package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.entities.Wallet;
import com.smnk107.uber.uberApp.entities.WalletTransaction;
import com.smnk107.uber.uberApp.entities.enums.TransactionMethod;
import com.smnk107.uber.uberApp.entities.enums.TransactionType;
import com.smnk107.uber.uberApp.exceptions.ResourceNotFoundException;
import com.smnk107.uber.uberApp.repository.WalletRepository;
import com.smnk107.uber.uberApp.services.WalletService;
import com.smnk107.uber.uberApp.services.WalletTransactionService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Builder
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper;
    private final WalletTransactionService walletTransactionService;
    @Override
    @Transactional
    public Wallet addMoneyToWallet(User user, Double money, String trnId, Ride ride,
                                   TransactionMethod transactionMethod) {

        Wallet wallet = walletRepository.findByUser(user).orElseThrow(()->new ResourceNotFoundException("Wallet for the user not found"));
        wallet.setBalance(wallet.getBalance()+money);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                                                .transactionId(trnId)
                                                .ride(ride)
                                                .transactionType(TransactionType.CREDIT)
                                                .transactionMethod(transactionMethod)
                                                .amount(money)
                                                .build();

        //walletTransactionService.createNewWalletTransaction(walletTransaction);

        wallet.getTransactions().add(walletTransaction);
        return walletRepository.save(wallet);
        //return null;
    }

    @Override
    public void clearWallet() {

    }

    @Override
    public Wallet deductMoneyFromWallet(User user, Double money, String trnId, Ride ride,
                                        TransactionMethod transactionMethod) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("Wallet for the user not found"));
        wallet.setBalance(wallet.getBalance()-money);

        WalletTransaction walletTransaction = WalletTransaction.builder()
                .transactionId(trnId)
                .ride(ride)
                .transactionType(TransactionType.DEBIT)
                .transactionMethod(transactionMethod)
                .amount(money)
                .build();

        walletTransactionService.createNewWalletTransaction(walletTransaction);
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) {

        walletRepository.findById(walletId).orElseThrow(()->new ResourceNotFoundException("Not found wallet, with id :"+walletId));
        return null;
    }

    @Override
    public Wallet createNewWallet(User user) {

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(0.0);

        walletRepository.save(wallet);
        return null;
    }

    @Override
    public Wallet findByUser(User user) {
        Wallet wallet = walletRepository.findByUser(user)
                .orElseThrow(()->new ResourceNotFoundException("No wallet found for given User"));
        return null;
    }
}
