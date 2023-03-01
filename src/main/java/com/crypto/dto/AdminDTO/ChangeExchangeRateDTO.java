package com.crypto.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChangeExchangeRateDTO {
    private String secret_key;
    private String base_currency;
    private BigDecimal BTC = BigDecimal.valueOf(1);
    private BigDecimal TON = BigDecimal.valueOf(1);
    private BigDecimal RUB = BigDecimal.valueOf(1);
}
