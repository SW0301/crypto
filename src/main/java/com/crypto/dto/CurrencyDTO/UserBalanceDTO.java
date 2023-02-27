package com.crypto.dto.CurrencyDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class UserBalanceDTO {
    private BigDecimal BTC_wallet;
    private BigDecimal TON_wallet;
    private BigDecimal RUB_wallet;
}
