package com.smnk107.uber.uberApp.dto;

import com.smnk107.uber.uberApp.entities.Ride;
import com.smnk107.uber.uberApp.entities.Wallet;
import com.smnk107.uber.uberApp.entities.enums.TransactionMethod;
import com.smnk107.uber.uberApp.entities.enums.TransactionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
public class WalletTransactionDTO {
    private Long id;
    private Double amount;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
    private RideDTO rideDTO;
    private String transactionId;
    private WalletDTO walletDTO;
    private LocalDateTime timeStamp;
}
