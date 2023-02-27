package com.crypto.repository;


import com.crypto.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    ExchangeRate findByCurrency(String currency);
}
