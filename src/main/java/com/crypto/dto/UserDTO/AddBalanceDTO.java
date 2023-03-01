package com.crypto.dto.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class AddBalanceDTO {
    private String secret_key;
    private BigDecimal RUB_wallet;
}
