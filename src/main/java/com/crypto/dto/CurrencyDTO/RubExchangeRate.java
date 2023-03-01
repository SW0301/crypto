package com.crypto.dto.CurrencyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class RubExchangeRate {
    private BigDecimal TON;
    private BigDecimal BTC;
}
