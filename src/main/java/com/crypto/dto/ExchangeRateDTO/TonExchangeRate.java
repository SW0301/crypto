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
public class TonExchangeRate {
    private BigDecimal RUB;
    private BigDecimal BTC;
}
