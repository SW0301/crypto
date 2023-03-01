package com.crypto.controller;

import com.crypto.dto.CurrencyDTO.RubWalletDTO;
import com.crypto.dto.UserDTO.UserBalanceDTO;
import com.crypto.dto.UserDTO.*;
import com.crypto.exception.AppError;
import com.crypto.service.UserService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO UserDTO) {
        SecretKeyDTO secretKeyDTO = new SecretKeyDTO();
        if (userService.emailExistence(UserDTO.getUserName()) ||
                userService.userNameExistence(UserDTO.getUserName())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Already exists email or user name"), HttpStatus.BAD_REQUEST);
        } else {
            secretKeyDTO.setSecret_key(userService.createUser(UserDTO));
            return new ResponseEntity<>(secretKeyDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<?> checkBalance(@RequestBody UserDTO UserDTO) {
        if (UserDTO != null && userService.secretKeyExistence(UserDTO.getSecret_key())) {
            UserBalanceDTO balanceDTO = userService.checkBalance(UserDTO.getSecret_key());
            return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
        } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "There is no such secret key"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBalance")
    public ResponseEntity<?> addBalance(@RequestBody UserDTO userDTO) {
        var response = userService.addBalance(userDTO.getSecret_key(), userDTO.getRub_wallet());
        if (userDTO != null && userService.secretKeyExistence(userDTO.getSecret_key()))
            return new ResponseEntity<>(response, HttpStatus.OK);
        else return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                "There is no such secret key"), HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/withdrawalOfMoney")
    public ResponseEntity<Object> withdrawalOfMoney(@RequestBody WithdrawalOfMoneyDTO withdrawalOfMoneyDTO) {
        String currency = withdrawalOfMoneyDTO.getCurrency();
        String secretKey = withdrawalOfMoneyDTO.getSecret_key();
        BigDecimal count = withdrawalOfMoneyDTO.getCount();
        if (withdrawalOfMoneyDTO != null && userService.secretKeyExistence(secretKey) && secretKey != null) {
            var response = userService.withdrawalOfMoney(secretKey, currency, count);
            if (response.getClass().equals("class com.crypto.exepion.AppError")) {
                AppError appError = (AppError) response;
                return new ResponseEntity<>(appError, HttpStatus.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "There is no such secret key"), HttpStatus.NOT_FOUND);
    }


    @PostMapping("/currencyExchange")
    public ResponseEntity<?> currencyExchange(@RequestBody CurrencyExchangeDTO currencyExchangeDTO) {
        if (userService.secretKeyExistence(currencyExchangeDTO.getSecret_key()) && currencyExchangeDTO != null) {
            String secretKey = currencyExchangeDTO.getSecret_key();
            String currencyFrom = currencyExchangeDTO.getCurrency_from();
            String currencyTo = currencyExchangeDTO.getCurrency_to();
            BigDecimal amount = currencyExchangeDTO.getAmount();
            var response = userService.currencyExchange(secretKey, currencyFrom, currencyTo, amount);

            if (response.getClass().equals("class com.crypto.exepion.AppError")) {
                AppError appError = (AppError) response;
                return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "There is no such secret key"), HttpStatus.BAD_REQUEST);
        }
    }

}
