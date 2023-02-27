package com.crypto.service;

import com.crypto.dto.CurrencyDTO.UserBalanceDTO;
import com.crypto.dto.CreateUserDTO;


import java.math.BigDecimal;


public interface UserService {
    String createUser(CreateUserDTO createUserDTO);
    UserBalanceDTO checkBalance(String secretKey);
    BigDecimal addBalance(String secretKey, BigDecimal rubWallet);
    Object withdrawalOfMoney(String secretKey, String currency, BigDecimal count);
    Object currentExchangeRates(String currency);
    void currencyExchange(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount);

    String getKey();

    boolean emailExistence(String email);
    boolean userNameExistence(String userName);
    boolean secretKeyExistence(String secretKey);
}
