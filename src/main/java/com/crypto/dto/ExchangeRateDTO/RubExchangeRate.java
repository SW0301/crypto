package com.crypto.dto.ExchangeRateDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RubExchangeRate {
    private BigDecimal TON;
    private BigDecimal BTC;
}
