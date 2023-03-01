package com.crypto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transaction", schema = "public")
@Entity
public class Transaction {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_id_generator")
    @SequenceGenerator(name = "transaction_id_generator", sequenceName = "transaction_id_seq", allocationSize = 1)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name = "currency_from")
    private String currencyFrom;

    @Column(name = "currency_to")
    private String currencyTo;

    @Column(name = "amount_from")
    private BigDecimal amountFrom;

    @Column(name="amount_to")
    private BigDecimal amountTo;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of")
    private LocalDate dateOf;
}
