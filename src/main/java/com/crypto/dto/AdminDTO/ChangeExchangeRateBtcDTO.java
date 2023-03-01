package com.crypto.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ChangeExchangeRateBtcDTO {
    private String secret_key;
    private String base_currency;
    private BigDecimal TON;
    private BigDecimal RUB;
}
