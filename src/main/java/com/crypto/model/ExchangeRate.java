package com.crypto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchange_rate", schema = "public")
public class ExchangeRate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exchange_rate_id_generator")
    @SequenceGenerator(name = "exchange_rate_id_generator", sequenceName = "exchange_rate_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name="currency_in_rub")
    private BigDecimal currencyInRub;

    @Column(name="currency_in_ton")
    private BigDecimal currencyInTon;

    @Column(name="currency_in_btc")
    private BigDecimal currencyInBtc;
}
