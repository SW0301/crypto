package com.crypto.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CurrencyExchangeDTO {
    private String secret_key;
    private String currency_from;
    private String currency_to;
    private BigDecimal amount;
}
