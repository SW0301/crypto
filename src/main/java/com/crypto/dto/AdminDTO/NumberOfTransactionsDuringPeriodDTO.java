package com.crypto.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NumberOfTransactionsDuringPeriodDTO {
    private String secret_key;
    private String date_from;
    private String date_to;
}
