package com.crypto.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private String userName;
    private String email;
    private String secret_key;
    private BigDecimal rub_wallet;
    private BigDecimal btc_wallet;
    private BigDecimal ton_wallet;
}
