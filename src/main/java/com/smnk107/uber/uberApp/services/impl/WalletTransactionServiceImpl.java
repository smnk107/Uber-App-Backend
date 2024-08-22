package com.smnk107.uber.uberApp.services.impl;

import com.smnk107.uber.uberApp.dto.WalletTransactionDTO;
import com.smnk107.uber.uberApp.entities.WalletTransaction;
import com.smnk107.uber.uberApp.repository.WalletTransactionRepository;
import com.smnk107.uber.uberApp.services.WalletTransactionService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Data
@RequiredArgsConstructor
@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {


    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createNewWalletTransaction(WalletTransaction walletTransaction) {
       // WalletTransaction walletTransaction = modelMapper.map(walletTransactionDTO,WalletTransaction.class);
        walletTransactionRepository.save(walletTransaction);
    }
}
