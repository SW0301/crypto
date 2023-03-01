package com.crypto.controller;

import com.crypto.dto.SecretKeyCurrencyDTO;
import com.crypto.exeption.AppError;
import com.crypto.service.AdminService;
import com.crypto.service.ExchangeRateService;
import com.crypto.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;
    private final UserService userService;
    private final AdminService adminService;

    public ExchangeRateController(ExchangeRateService exchangeRateService, UserService userService, AdminService adminService) {
        this.exchangeRateService = exchangeRateService;
        this.userService = userService;
        this.adminService = adminService;
    }

    @GetMapping("/currentExchangeRates")
    public ResponseEntity<Object> currentExchangeRates(@RequestBody SecretKeyCurrencyDTO secretKeyCurrencyDTO) {
        String currency = secretKeyCurrencyDTO.getCurrency();
        String secretKey = secretKeyCurrencyDTO.getSecret_key();
        if (userService.secretKeyExistence(secretKey) || adminService.secretKeyExistence(secretKey)) {
            Object currentExchangeRates = exchangeRateService.currentExchangeRates(currency);
            if (currentExchangeRates.getClass().equals("class com.cpypto.exeption.AppError")) {
                AppError appError = (AppError) currentExchangeRates;
                return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(currentExchangeRates, HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "There is no such secret key"), HttpStatus.NOT_FOUND);
    }
}
