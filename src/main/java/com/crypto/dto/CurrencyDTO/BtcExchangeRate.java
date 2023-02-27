package com.crypto.dto.CurrencyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class BtcExchangeRate {
    private BigDecimal RUB;
    private BigDecimal TON;
}
