package com.crypto.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class WithdrawalOfMoneyDTO {
    private String secret_key;
    private String currency;
    private BigDecimal count;
    private String credit_card;
    private String wallet;
}
