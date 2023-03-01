package com.crypto.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ChangeExchangeRateTonDTO {
    private String secret_key;
    private String base_currency;
    private BigDecimal BTC;
    private BigDecimal RUB;
}
