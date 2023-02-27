package com.crypto.controller;

import com.crypto.dto.*;
import com.crypto.dto.CurrencyDTO.RubWalletDTO;
import com.crypto.dto.CurrencyDTO.UserBalanceDTO;
import com.crypto.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public SecretKeyDTO createUser(@RequestBody CreateUserDTO createUserDTO) {
        SecretKeyDTO createUserDTOResponse = new SecretKeyDTO();
        if (userService.emailExistence(createUserDTO.getUserName()) ||
                userService.userNameExistence(createUserDTO.getUserName())) {
            createUserDTOResponse.setSecret_key("Отказано в регистрации");
            return createUserDTOResponse;
        } else createUserDTOResponse.setSecret_key(userService.createUser(createUserDTO));
        return createUserDTOResponse;
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<UserBalanceDTO> checkBalance(@RequestBody SecretKeyDTO secretKeyDTO) {
        if (secretKeyDTO != null && userService.secretKeyExistence(secretKeyDTO.getSecret_key())) {
            UserBalanceDTO balanceDTO = userService.checkBalance(secretKeyDTO.getSecret_key());
            return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addBalance")
    public ResponseEntity<RubWalletDTO> addBalance(@RequestBody AddBalanceDTO addBalanceDTO) {
        if (addBalanceDTO != null && userService.secretKeyExistence(addBalanceDTO.getSecret_key()))
            return new ResponseEntity<>(new RubWalletDTO(userService.addBalance(addBalanceDTO.getSecret_key(),
                    addBalanceDTO.getRUB_wallet())), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/withdrawalOfMoney")
    public ResponseEntity<Object> withdrawalOfMoney(@RequestBody WithdrawalOfMoneyDTO withdrawalOfMoneyDTO) {
        String currency = withdrawalOfMoneyDTO.getCurrency();
        String secretKey = withdrawalOfMoneyDTO.getSecret_key();
        BigDecimal count = withdrawalOfMoneyDTO.getCount();

        if (withdrawalOfMoneyDTO != null && userService.secretKeyExistence(secretKey) && secretKey != null) {
            UserBalanceDTO userBalanceDTO = userService.checkBalance(secretKey);
            if ((currency.equalsIgnoreCase("rub") && userBalanceDTO.getRUB_wallet().compareTo(count) >= 0) ||
                    (currency.equalsIgnoreCase("ton") && userBalanceDTO.getTON_wallet().compareTo(count) >= 0)
                    || (currency.equalsIgnoreCase("btc") && userBalanceDTO.getBTC_wallet().compareTo(count) >= 0))
                return new ResponseEntity<>(userService.withdrawalOfMoney(secretKey, currency, count), HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/currentExchangeRates")
    public ResponseEntity<Object> currentExchangeRates(@RequestBody CurrentExchangeRatesDTO currentExchangeRatesDTO) {
        String currency = currentExchangeRatesDTO.getCurrency();
        String secretKey = currentExchangeRatesDTO.getSecret_key();
        if (userService.secretKeyExistence(secretKey) && (currency.equalsIgnoreCase("ton") ||
                currency.equalsIgnoreCase("btc"))) {
            Object currentExchangeRates = userService.currentExchangeRates(currency);
            return new ResponseEntity<>(currentExchangeRates, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
