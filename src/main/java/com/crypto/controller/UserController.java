package com.crypto.controller;

import com.crypto.dto.*;
import com.crypto.dto.CurrencyDTO.RubWalletDTO;
import com.crypto.dto.CurrencyDTO.UserBalanceDTO;
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
    public ResponseEntity<?> createUser(@RequestBody CreateUserDTO createUserDTO) {
        SecretKeyDTO secretKeyDTO = new SecretKeyDTO();
        if (userService.emailExistence(createUserDTO.getUserName()) ||
                userService.userNameExistence(createUserDTO.getUserName())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    "Already exists email or user name"), HttpStatus.BAD_REQUEST);
        } else {
            secretKeyDTO.setSecret_key(userService.createUser(createUserDTO));
            return new ResponseEntity<>(secretKeyDTO, HttpStatus.OK);
        }
    }

    @GetMapping("/checkBalance")
    public ResponseEntity<?> checkBalance(@RequestBody SecretKeyDTO secretKeyDTO) {
        if (secretKeyDTO != null && userService.secretKeyExistence(secretKeyDTO.getSecret_key())) {
            UserBalanceDTO balanceDTO = userService.checkBalance(secretKeyDTO.getSecret_key());
            return new ResponseEntity<>(balanceDTO, HttpStatus.OK);
        } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                "There is no such secret key"), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBalance")
    public ResponseEntity<?> addBalance(@RequestBody AddBalanceDTO addBalanceDTO) {
        if (addBalanceDTO != null && userService.secretKeyExistence(addBalanceDTO.getSecret_key()))
            return new ResponseEntity<>(new RubWalletDTO(userService.addBalance(addBalanceDTO.getSecret_key(),
                    addBalanceDTO.getRUB_wallet())), HttpStatus.OK);
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
