package com.crypto.repository;

import com.crypto.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT count(id) FROM Transaction WHERE dateOf >= :dateFrom and dateOf<= :dateTo")
    int countOfTransactionsDuringPeriod(@Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
