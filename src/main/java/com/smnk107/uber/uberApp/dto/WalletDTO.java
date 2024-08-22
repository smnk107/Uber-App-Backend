package com.smnk107.uber.uberApp.dto;

import com.smnk107.uber.uberApp.entities.User;
import com.smnk107.uber.uberApp.entities.WalletTransaction;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WalletDTO {
    private Long id;
    private UserDTO userDTO;
    private Double balance;
    private List<WalletTransactionDTO> transactions;
}
