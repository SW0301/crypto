package com.crypto.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class NumberOfTransactionsDuringPeriodDTO {
    private String secret_key;
    private String date_from;
    private String date_to;
}
