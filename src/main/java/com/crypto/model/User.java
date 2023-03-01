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
@Table(name = "pguser", schema = "public")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pguser_id_generator")
    @SequenceGenerator(name = "pguser_id_generator", sequenceName = "pguser_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "rub_wallet")
    private BigDecimal rubWallet;

    @Column(name = "btc_wallet")
    private BigDecimal btcWallet;

    @Column(name = "ton_wallet")
    private BigDecimal tonWallet;

}
