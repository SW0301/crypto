package com.crypto.service.impl;

import com.crypto.repository.AdminRepository;
import com.crypto.service.AdminService;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


    @Override
    public void changeExchangeRate(String secretKey, String baseCurrency, BigDecimal btcWallet, BigDecimal rubWallet) {

    }

    @Override
    public BigDecimal totalAmountAllAccounts(String secretKey, String currency) {
        return null;
    }

    @Override
    public void numberOfOperationsDuringPeriod(String secretKey, Date dateFrom, Date dateTo) {

    }

    @Override
    public void currentExchangeRates(String secretKey, String currency) {

    }
}
