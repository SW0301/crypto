package com.crypto.service;



import java.math.BigDecimal;


public interface AdminService {
    Object changeExchangeRate(String baseCurrency, BigDecimal tonWallet, BigDecimal rubWallet, BigDecimal btcWallet);

    Object totalAmountAllAccounts(String currency);

    Object numberOfOperationsDuringPeriod(String dateFrom, String dateTo);


    boolean secretKeyExistence(String secretKey);
}
