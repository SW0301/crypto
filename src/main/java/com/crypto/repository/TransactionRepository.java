package com.crypto.repository;

import com.crypto.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
