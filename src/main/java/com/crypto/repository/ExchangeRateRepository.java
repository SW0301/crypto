package com.crypto.repository;


import com.crypto.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    ExchangeRate findByCurrency(String currency);

    @Modifying
    @Transactional
    @Query(value = "UPDATE ExchangeRate SET currencyInBtc = :currencyInBtc, currencyInRub = :currencyInRub WHERE currency ='TON'")
    void changeExchangeRateTon(@Param("currencyInBtc")BigDecimal currencyInBtc, @Param("currencyInRub") BigDecimal currencyInRub);
    @Modifying
    @Transactional
    @Query(value = "UPDATE ExchangeRate SET currencyInBtc = :currencyInTon, currencyInRub = :currencyInRub WHERE currency ='BTC'")
    void changeExchangeRateBtc(@Param("currencyInTon")BigDecimal currencyInTon, @Param("currencyInRub") BigDecimal currencyInRub);
}
