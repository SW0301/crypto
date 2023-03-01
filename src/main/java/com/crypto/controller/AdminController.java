package com.crypto.controller;

import com.crypto.dto.AdminDTO.ChangeExchangeRateBtcDTO;
import com.crypto.dto.AdminDTO.ChangeExchangeRateTonDTO;
import com.crypto.dto.AdminDTO.NumberOfTransactionsDuringPeriodDTO;
import com.crypto.dto.SecretKeyCurrencyDTO;
import com.crypto.exeption.AppError;
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

//    @PostMapping("/changeExchangeRate")
//    public ResponseEntity<?> changeExchangeRate(@RequestBody ChangeExchangeRateTonDTO changeExchangeRateTonDTO,
//                                                @RequestBody ChangeExchangeRateBtcDTO changeExchangeRateBtcDTO) {
//
//        if (changeExchangeRateBtcDTO == null && changeExchangeRateTonDTO == null) {
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
//                    " "), HttpStatus.BAD_GATEWAY);
//        } else if (changeExchangeRateTonDTO == null && changeExchangeRateBtcDTO != null) {
//            String secretKey = changeExchangeRateBtcDTO.getSecret_key();
//            if (adminService.secretKeyExistence(secretKey)) {
//                String baseCurrency = changeExchangeRateBtcDTO.getBase_currency();
//                BigDecimal tonWallet = changeExchangeRateBtcDTO.getTON();
//                BigDecimal rubWallet = changeExchangeRateBtcDTO.getRUB();
//                var response = adminService.changeExchangeRate(baseCurrency, tonWallet, rubWallet);
//                if (response.getClass().equals("class com.crypto.exeption.AppError")) {
//                    AppError appError = (AppError) response;
//                    return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
//                } else {
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                }
//            } else return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
//                    "There is no such secret key"), HttpStatus.NOT_FOUND);
//        } else if (changeExchangeRateBtcDTO == null && changeExchangeRateTonDTO != null) {
//            String secretKey = changeExchangeRateTonDTO.getSecret_key();
//            if (adminService.secretKeyExistence(secretKey)) {
//                String baseCurrency = changeExchangeRateTonDTO.getBase_currency();
//                BigDecimal btcWallet = changeExchangeRateTonDTO.getBTC();
//                BigDecimal rubWallet = changeExchangeRateTonDTO.getRUB();
//                var response = adminService.changeExchangeRate(baseCurrency, btcWallet, rubWallet);
//                if (response.getClass().equals("class com.crypto.exeption.AppError")) {
//                    AppError appError = (AppError) response;
//                    return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
//                } else {
//                    return new ResponseEntity<>(response, HttpStatus.OK);
//                }
//            } else
//                return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
//                        "There is no such secret key"), HttpStatus.NOT_FOUND);
//        } else
//            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),
//                    " Bad Request"), HttpStatus.BAD_GATEWAY);
//    }


    @GetMapping("/totalAmountAllAccounts")
    public ResponseEntity<?> totalAmountAllAccounts(@RequestBody SecretKeyCurrencyDTO secretKeyCurrencyDTO) {
        if (secretKeyCurrencyDTO != null && adminService.secretKeyExistence(secretKeyCurrencyDTO.getSecret_key())) {
            var response = adminService.totalAmountAllAccounts(secretKeyCurrencyDTO.getCurrency());
            if (response.getClass().equals("class com.crypto.exeption.AppError")) {
                AppError appError = new AppError();
                return new ResponseEntity<>(appError, HttpStatusCode.valueOf(appError.getStatusCode()));
            } else {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } else
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "There is no such secret key"), HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(),
                    "There is no such secret key"), HttpStatus.NOT_FOUND);
    }

}
