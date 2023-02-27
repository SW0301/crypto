package com.crypto.service.impl;

import com.crypto.dto.CurrencyDTO.*;
import com.crypto.dto.CreateUserDTO;
import com.crypto.model.ExchangeRate;
import com.crypto.model.User;
import com.crypto.repository.ExchangeRateRepository;
import com.crypto.repository.UserRepository;
import com.crypto.service.UserService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ExchangeRateRepository exchangeRateRepository;

    public UserServiceImpl(UserRepository userRepository, ExchangeRateRepository exchangeRateRepository) {
        this.userRepository = userRepository;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public String createUser(CreateUserDTO createUserDTO) {
        User user = new User();
        user.setUserName(createUserDTO.getUserName());
        user.setEmail(createUserDTO.getEmail());
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
    public BigDecimal addBalance(String secretKey, BigDecimal rubWallet) {
        userRepository.addBalance(rubWallet, secretKey);
        return userRepository.findBySecretKey(secretKey).getRubWallet();
    }

    @Override
    public Object withdrawalOfMoney(String secretKey, String currency, BigDecimal count) {
        User user = userRepository.findBySecretKey(secretKey);
        if (currency.equalsIgnoreCase("rub")) {
            userRepository.withdrawalOfBalanceRUB(secretKey, count);
            user.setRubWallet(user.getRubWallet().subtract(count));
            return new RubWalletDTO(user.getRubWallet());
        } else if (currency.equalsIgnoreCase("ton")) {
            userRepository.withdrawalOfBalanceTON(secretKey, count);
            user.setTonWallet(user.getTonWallet().subtract(count));
            return new TonWalletDTO(user.getTonWallet());
        } else {
            userRepository.withdrawalOfBalanceBTC(secretKey, count);
            user.setBtcWallet(user.getBtcWallet().subtract(count));
            return new BtcWalletDTO(user.getBtcWallet());
        }
    }

    @Override
    public Object currentExchangeRates(String currency) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(currency.toUpperCase());
        if (currency.equalsIgnoreCase("ton")) {
            TonExchangeRate tonExchangeRate = new TonExchangeRate(exchangeRate.getCurrencyInRub(), exchangeRate.getCurrencyInBtc());
            return tonExchangeRate;
        }
        else {
            BtcExchangeRate btcExchangeRate = new BtcExchangeRate(exchangeRate.getCurrencyInRub(), exchangeRate.getCurrencyInTon());
            return btcExchangeRate;
        }
    }

    @Override
    public void currencyExchange(String secretKey, String currencyFrom, String currencyTo, BigDecimal amount) {

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
