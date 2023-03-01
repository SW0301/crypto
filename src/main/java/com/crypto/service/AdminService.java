package com.crypto.service;



import java.math.BigDecimal;
import java.util.Date;


public interface AdminService {
    Object changeExchangeRate(String baseCurrency, BigDecimal btcWallet, BigDecimal rubWallet);

    Object totalAmountAllAccounts(String currency);

    Object numberOfOperationsDuringPeriod(String dateFrom, String dateTo);


    boolean secretKeyExistence(String secretKey);
}
