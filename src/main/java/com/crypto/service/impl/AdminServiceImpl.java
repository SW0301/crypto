package com.crypto.service.impl;

import com.crypto.dto.AdminDTO.TransactionCountDTO;
import com.crypto.dto.CurrencyDTO.*;
import com.crypto.dto.ExchangeRateDTO.BtcExchangeRate;
import com.crypto.dto.ExchangeRateDTO.TonExchangeRate;
import com.crypto.exception.AppError;
import com.crypto.model.ExchangeRate;
import com.crypto.repository.AdminRepository;
import com.crypto.repository.ExchangeRateRepository;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.time.LocalDate;


@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRepository userRepository, TransactionRepository transactionRepository,
                            ExchangeRateRepository exchangeRateRepository) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }


    @Override
    public Object changeExchangeRate(String baseCurrency, BigDecimal tonWallet, BigDecimal rubWallet, BigDecimal btcWallet) {
        if(baseCurrency.equalsIgnoreCase("ton")) {
            exchangeRateRepository.changeExchangeRateTon(btcWallet, rubWallet);
            ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(baseCurrency);
            TonExchangeRate tonExchangeRate = new TonExchangeRate(exchangeRate.getCurrencyInRub(),
                    exchangeRate.getCurrencyInBtc());
            return tonExchangeRate;
        }
        else if (baseCurrency.equalsIgnoreCase("btc")) {
            exchangeRateRepository.changeExchangeRateBtc(tonWallet, rubWallet);
            ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(baseCurrency);
            BtcExchangeRate btcExchangeRate = new BtcExchangeRate(exchangeRate.getCurrencyInRub(),
                    exchangeRate.getCurrencyInTon());
            return btcExchangeRate;
        }
        else return  new AppError(HttpStatus.BAD_REQUEST.value(), " Bad  base currency");
    }

    @Override
    public Object totalAmountAllAccounts(String currency) {
        if(currency.equalsIgnoreCase("rub")) {
            RubWalletDTO rubWalletDTO = new RubWalletDTO();
            rubWalletDTO.setRUB_wallet(userRepository.rubSumAllUsers());
            return rubWalletDTO;
        }
        else if(currency.equalsIgnoreCase("ton")) {
            TonWalletDTO tonWalletDTO = new TonWalletDTO();
            tonWalletDTO.setTON_wallet(userRepository.tonSumAllUsers());
            return tonWalletDTO;
        }
        else if (currency.equalsIgnoreCase("btc")) {
            BtcWalletDTO btcWalletDTO = new BtcWalletDTO();
            btcWalletDTO.setBTC_wallet(userRepository.btcSumAllUsers());
            return btcWalletDTO;
        }
        else
            return new AppError(HttpStatus.BAD_REQUEST.value(), "Not correct currency");
    }

    @Override
    public Object numberOfOperationsDuringPeriod(String dateFromS, String dateToS) {
        LocalDate dateFrom = LocalDate.parse(dateFromS);
        LocalDate dateTo = LocalDate.parse(dateToS);
        if(dateFrom.isBefore(dateTo)) {
            TransactionCountDTO transactionCountDTO = new TransactionCountDTO();
            transactionCountDTO.setTransaction_count(
                    transactionRepository.countOfTransactionsDuringPeriod(dateFrom, dateTo));
            return  transactionCountDTO;
        }
        else
            return new AppError(HttpStatus.BAD_REQUEST.value(), "Bad dates");
    }



    @Override
    public boolean secretKeyExistence(String secretKey) {
        return adminRepository.existsBySecretKey(secretKey);
    }
}
