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
public class BtcExchangeRate {
    private BigDecimal RUB;
    private BigDecimal TON;
}
