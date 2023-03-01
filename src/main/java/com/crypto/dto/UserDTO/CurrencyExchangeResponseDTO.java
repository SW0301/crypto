package com.crypto.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class CurrencyExchangeResponseDTO {
    private String currency_from;
    private String currency_to;
    private BigDecimal amount_from;
    private BigDecimal amount_to;
}
