package com.crypto.dto.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class WithdrawalOfMoneyDTO {
    private String secret_key;
    private String currency;
    private BigDecimal count;
    private String wallet;
}
