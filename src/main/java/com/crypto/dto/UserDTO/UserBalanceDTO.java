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
public class UserBalanceDTO {
    private BigDecimal BTC_wallet;
    private BigDecimal TON_wallet;
    private BigDecimal RUB_wallet;
}
