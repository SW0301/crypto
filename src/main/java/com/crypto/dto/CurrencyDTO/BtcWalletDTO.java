package com.crypto.dto.CurrencyDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class BtcWalletDTO {
    private BigDecimal BTC_wallet;
}
