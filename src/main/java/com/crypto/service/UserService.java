package com.crypto.service;

import com.crypto.dto.UserDTO.UserBalanceDTO;
import com.crypto.dto.UserDTO.UserDTO;


import java.math.BigDecimal;


public interface UserService {
    String createUser(UserDTO UserDTO);

    UserBalanceDTO checkBalance(String secretKey);

    Object addBalance(String secretKey, BigDecimal rubWallet);

    Object withdrawalOfMoney(String secretKey, String currency, BigDecimal count);

    Object currencyExchange(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount);

    String getKey();

    boolean emailExistence(String email);

    boolean userNameExistence(String userName);

    boolean secretKeyExistence(String secretKey);
}
