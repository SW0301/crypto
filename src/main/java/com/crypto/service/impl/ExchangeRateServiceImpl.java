package com.crypto.service.impl;

import com.crypto.dto.CurrencyDTO.BtcExchangeRate;
import com.crypto.dto.CurrencyDTO.RubExchangeRate;
import com.crypto.dto.CurrencyDTO.TonExchangeRate;
import com.crypto.exeption.AppError;
import com.crypto.model.ExchangeRate;
import com.crypto.repository.ExchangeRateRepository;
import com.crypto.service.ExchangeRateService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public Object currentExchangeRates(String currency) {
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(currency.toUpperCase());
        switch (currency.toLowerCase()) {
            case "ton": {
                TonExchangeRate tonExchangeRate = new TonExchangeRate(exchangeRate.getCurrencyInRub(),
                        exchangeRate.getCurrencyInBtc());
                return tonExchangeRate;
            }
            case "btc": {
                BtcExchangeRate btcExchangeRate = new BtcExchangeRate(exchangeRate.getCurrencyInRub(),
                        exchangeRate.getCurrencyInTon());
                return btcExchangeRate;
            }
            case "rub": {
                RubExchangeRate rubExchangeRate = new RubExchangeRate(exchangeRate.getCurrencyInTon(),
                        exchangeRate.getCurrencyInBtc());
                return rubExchangeRate;
            }
            default: {
                return new AppError(HttpStatus.BAD_REQUEST.value(), "Bad currency");
            }
        }
    }
}
