package com.crypto.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;


public interface AdminService {
    void changeExchangeRate(String secretKey, String baseCurrency, BigDecimal btcWallet, BigDecimal rubWallet);
    BigDecimal totalAmountAllAccounts(String secretKey, String currency);
    void numberOfOperationsDuringPeriod(String secretKey, Date dateFrom, Date dateTo);
    void currentExchangeRates(String secretKey, String currency);
}
