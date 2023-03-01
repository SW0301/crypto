package com.crypto.service.impl;

import com.crypto.dto.CurrencyDTO.*;
import com.crypto.dto.ExchangeRateDTO.BtcExchangeRate;
import com.crypto.dto.ExchangeRateDTO.RubExchangeRate;
import com.crypto.dto.ExchangeRateDTO.TonExchangeRate;
import com.crypto.dto.UserDTO.TransactionDTO;
import com.crypto.dto.UserDTO.UserBalanceDTO;
import com.crypto.dto.UserDTO.UserDTO;
import com.crypto.exception.AppError;
import com.crypto.model.Transaction;
import com.crypto.model.User;
import com.crypto.repository.TransactionRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.ExchangeRateService;
import com.crypto.service.UserService;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ExchangeRateService exchangeRateService;

    public UserServiceImpl(UserRepository userRepository,
                           TransactionRepository transactionRepository, ExchangeRateService exchangeRateService) {
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.exchangeRateService = exchangeRateService;
    }

    @Override
    public String createUser(UserDTO UserDTO) {
        User user = new User();
        user.setUserName(UserDTO.getUserName());
        user.setEmail(UserDTO.getEmail());
        user.setRubWallet(BigDecimal.valueOf(0));
        user.setBtcWallet(BigDecimal.valueOf(0));
        user.setTonWallet(BigDecimal.valueOf(0));
        String secretKey = getKey();
        user.setSecretKey(secretKey);
        userRepository.save(user);
        return secretKey;
    }

    @Override
    public UserBalanceDTO checkBalance(String secretKey) {
        User user = userRepository.findBySecretKey(secretKey);
        UserBalanceDTO balanceDTO = new UserBalanceDTO();
        balanceDTO.setBTC_wallet(user.getBtcWallet());
        balanceDTO.setRUB_wallet(user.getRubWallet());
        balanceDTO.setTON_wallet(user.getTonWallet());
        return balanceDTO;
    }

    @Override
    public Object addBalance(String secretKey, BigDecimal Wallet) {
        userRepository.addBalanceRub(Wallet, secretKey);
        RubWalletDTO rubWalletDTO = new RubWalletDTO(userRepository.findBySecretKey(secretKey).getRubWallet());
        return rubWalletDTO;
    }

    @Override
    public Object withdrawalOfMoney(String secretKey, String currency, BigDecimal count) {
        User user = userRepository.findBySecretKey(secretKey);
        UserBalanceDTO userBalanceDTO = checkBalance(secretKey);
        if ((currency.equalsIgnoreCase("rub") && userBalanceDTO.getRUB_wallet().compareTo(count) >= 0) ||
                (currency.equalsIgnoreCase("ton") && userBalanceDTO.getTON_wallet().compareTo(count) >= 0) ||
                (currency.equalsIgnoreCase("btc") && userBalanceDTO.getBTC_wallet().compareTo(count) >= 0)) {
            switch (currency.toLowerCase()) {
                case "rub": {
                    userRepository.subtractingTheBalanceRUB(secretKey, count);
                    user.setRubWallet(user.getRubWallet().subtract(count));
                    return new RubWalletDTO(user.getRubWallet());
                }
                case "ton": {
                    userRepository.subtractingTheBalanceTON(secretKey, count);
                    user.setTonWallet(user.getTonWallet().subtract(count));
                    return new TonWalletDTO(user.getTonWallet());
                }
                case "btc": {
                    userRepository.subtractingTheBalanceBTC(secretKey, count);
                    user.setBtcWallet(user.getBtcWallet().subtract(count));
                    return new BtcWalletDTO(user.getBtcWallet());
                }
                default:
                    return new AppError(HttpStatus.BAD_REQUEST.value(),
                            "Currency not found");
            }
        } else return new AppError(HttpStatus.BAD_REQUEST.value(),
                "Currency not found");
    }


    @Override
    public Object currencyExchange(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount) {
        BigDecimal amountTo;
        User user = userRepository.findBySecretKey(secretKey);

        Transaction transaction = new Transaction();
        transaction.setCurrencyFrom(currencyFrom);
        transaction.setCurrencyTo(currencyTo);
        transaction.setUserId(user.getId());


        if ((currencyFrom.equalsIgnoreCase("rub") &&
                user.getRubWallet().compareTo(amount) >= 0) || (currencyFrom.equalsIgnoreCase("ton") &&
                user.getTonWallet().compareTo(amount) >= 0) || (currencyFrom.equalsIgnoreCase("btc") &&
                user.getBtcWallet().compareTo(amount) >= 0)) {

            transaction.setAmountFrom(amount);
            transaction.setDateOf(LocalDate.now());

            switch (currencyTo.toLowerCase()) {
                case "rub": {
                    RubExchangeRate rubExchangeRate =
                            (RubExchangeRate) exchangeRateService.currentExchangeRates(currencyTo);
                    if (currencyFrom.equalsIgnoreCase("ton")) {
                        userRepository.subtractingTheBalanceTON(secretKey, amount);
                        amountTo = amount.divide(rubExchangeRate.getTON(), 2, RoundingMode.CEILING);
                    } else {
                        userRepository.subtractingTheBalanceBTC(secretKey, amount);
                        amountTo = amount.divide(rubExchangeRate.getBTC(), 2, RoundingMode.CEILING);
                    }
                    userRepository.addBalanceRub(amountTo, secretKey);
                    transaction.setAmountTo(amountTo);
                    break;
                }
                case "ton": {
                    TonExchangeRate tonExchangeRate =
                            (TonExchangeRate) exchangeRateService.currentExchangeRates(currencyTo);
                    if (currencyFrom.equalsIgnoreCase("rub")) {
                        userRepository.subtractingTheBalanceRUB(secretKey, amount);
                        amountTo = amount.divide(tonExchangeRate.getRUB(), 6, RoundingMode.CEILING);
                    } else {
                        userRepository.subtractingTheBalanceBTC(secretKey, amount);
                        amountTo = amount.divide(tonExchangeRate.getBTC(), 6, RoundingMode.CEILING);
                    }
                    transaction.setAmountTo(amountTo);
                    userRepository.addBalanceTon(amountTo, secretKey);
                    break;
                }
                case "btc": {
                    BtcExchangeRate btcExchangeRate =
                            (BtcExchangeRate) exchangeRateService.currentExchangeRates(currencyTo);
                    if (currencyFrom.equalsIgnoreCase("rub")) {
                        userRepository.subtractingTheBalanceRUB(secretKey, amount);
                        amountTo = amount.divide(btcExchangeRate.getRUB(), 8, RoundingMode.CEILING);
                    } else {
                        userRepository.subtractingTheBalanceTON(secretKey, amount);
                        amountTo = amount.divide(btcExchangeRate.getTON(), 8, RoundingMode.CEILING);
                    }
                    transaction.setAmountTo(amountTo);
                    userRepository.addBalanceBtc(amountTo, secretKey);
                    break;
                }
                default:
                    return new AppError(HttpStatus.BAD_REQUEST.value(), "Can't add or subtract Balance");
            }
            transactionRepository.save(transaction);
            TransactionDTO transactionDTO = new TransactionDTO(transaction.getCurrencyFrom(),
                    transaction.getCurrencyTo(), transaction.getAmountFrom(), transaction.getAmountTo());
            return transactionDTO;

        } else
            return new AppError(HttpStatus.NOT_FOUND.value(), "Not found currency or not enough money");
    }

    public String getKey() {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        keyGenerator.init(128);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] rawData = secretKey.getEncoded();
        String encodedKey = Base64.getEncoder().encodeToString(rawData);
        return encodedKey;
    }

    @Override
    public boolean emailExistence(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean userNameExistence(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public boolean secretKeyExistence(String secretKey) {
        return userRepository.existsBySecretKey(secretKey);
    }

}
