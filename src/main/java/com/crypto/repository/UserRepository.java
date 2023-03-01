package com.crypto.repository;

import com.crypto.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    boolean existsByUserName(String userName);

    boolean existsBySecretKey(String secretKey);

    User findBySecretKey(String secretKey);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET rubWallet = rubWallet + :rubWallet WHERE secretKey = :secretKey")
    void addBalanceRub(@Param("rubWallet") BigDecimal rubWallet, @Param("secretKey") String secretKey);


    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET rubWallet = rubWallet - :count WHERE secretKey = :secretKey ")
    void subtractingTheBalanceRUB(@Param("secretKey") String secretKey, @Param("count") BigDecimal count);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET tonWallet = tonWallet - :count WHERE secretKey = :secretKey ")
    void subtractingTheBalanceTON(@Param("secretKey") String secretKey, @Param("count") BigDecimal count);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET btcWallet = btcWallet - :count WHERE secretKey = :secretKey")
    void subtractingTheBalanceBTC(@Param("secretKey") String secretKey, @Param("count") BigDecimal count);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET tonWallet = tonWallet + :tonWallet WHERE secretKey = :secretKey")
    void addBalanceTon(@Param("tonWallet") BigDecimal tonWallet, @Param("secretKey") String secretKey);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET btcWallet = btcWallet + :btcWallet WHERE secretKey = :secretKey")
    void addBalanceBtc(@Param("btcWallet") BigDecimal btcWallet, @Param("secretKey") String secretKey);

    @Query(value = "SELECT SUM(rubWallet) FROM User")
    BigDecimal rubSumAllUsers();
    @Query(value = "SELECT SUM(tonWallet) FROM User")
    BigDecimal tonSumAllUsers();
    @Query(value = "SELECT SUM(btcWallet) FROM User")
    BigDecimal btcSumAllUsers();

}
