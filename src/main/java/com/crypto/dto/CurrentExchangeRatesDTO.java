package com.crypto.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CurrentExchangeRatesDTO {
    private String secret_key;
    private String currency;
}
