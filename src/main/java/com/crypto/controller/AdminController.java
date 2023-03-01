package com.crypto.controller;

import com.crypto.dto.AdminDTO.ChangeExchangeRateDTO;
import com.crypto.dto.AdminDTO.NumberOfTransactionsDuringPeriodDTO;
import com.crypto.dto.SecretKeyWithCurrencyDTO;
import com.crypto.exception.AppError;
import com.crypto.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/changeExchangeRate")
    public ResponseEntity<?> changeExchangeRate(@RequestBody ChangeExchangeRateDTO changeExchangeRateDTO) {
        if (changeExchangeRateDTO == null) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
                    " "), HttpStatus.BAD_GATEWAY);
        } else {
            String secretKey = changeExchangeRateDTO.getSecret_key();
            if (adminService.secretKeyExistence(secretKey)) {
                String baseCurrency = changeExchangeRateDTO.getBase_currency();
                BigDecimal btcWallet = changeExchangeRateDTO.getBTC();
                BigDecimal tonWallet = changeExchangeRateDTO.getTON();
                BigDecimal rubWallet = changeExchangeRateDTO.getRUB();
                var response = adminService.changeExchangeRate(baseCurrency, tonWallet, rubWallet, btcWallet);
                if (response.getClass().equals("class com.crypto.exception.AppError")) {
                    AppError appError = (AppError) response;
                    return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
                } else
                    return new ResponseEntity<>(response, HttpStatus.OK);

            } else return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                    "FORBIDDEN"), HttpStatus.FORBIDDEN);
        }
    }


    @GetMapping("/totalAmountAllAccounts")
    public ResponseEntity<?> totalAmountAllAccounts(@RequestBody SecretKeyWithCurrencyDTO secretKeyWithCurrencyDTO) {
        if (secretKeyWithCurrencyDTO != null && adminService.secretKeyExistence(secretKeyWithCurrencyDTO.getSecret_key())) {
            var response = adminService.totalAmountAllAccounts(secretKeyWithCurrencyDTO.getCurrency());
            if (response.getClass().equals("class com.crypto.exception.AppError")) {
                AppError appError = new AppError();
                return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                    "FORBIDDEN"), HttpStatus.FORBIDDEN);
    }

    @GetMapping("/numberOfOperationsDuringPeriod")
    public ResponseEntity<?> numberOfOperationsDuringPeriod(@RequestBody NumberOfTransactionsDuringPeriodDTO numberOfTransactionsDuringPeriodDTO) {
        String secretKey = numberOfTransactionsDuringPeriodDTO.getSecret_key();
        String dateFrom = numberOfTransactionsDuringPeriodDTO.getDate_from();
        String dateTo = numberOfTransactionsDuringPeriodDTO.getDate_to();
        if (numberOfTransactionsDuringPeriodDTO != null && adminService.secretKeyExistence(secretKey)) {
            var response = adminService.numberOfOperationsDuringPeriod(dateFrom, dateTo);
            if (response.getClass().equals("class com.crypto.exeprion.AppError")) {
                AppError appError = (AppError) response;
                return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new AppError(HttpStatus.FORBIDDEN.value(),
                    "FORBIDDEN"), HttpStatus.FORBIDDEN);
    }

}
