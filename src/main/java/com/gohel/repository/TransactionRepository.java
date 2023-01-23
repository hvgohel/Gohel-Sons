package com.gohel.repository;

import com.gohel.model.Transaction;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  @Query(
      "SELECT s FROM Transaction s WHERE s.user.id = ?1 ORDER BY STR_TO_DATE(date, '%d-%m-%Y') DESC")
  Page<Transaction> getAllByUser(Long userId, Pageable pageable);

  @Query(
      "SELECT s FROM Transaction s WHERE s.user.id = ?1 AND (description LIKE ?2 OR date = ?3 OR receivedDate = ?4 OR amount = ?5) ORDER BY STR_TO_DATE(date, '%d-%m-%Y') DESC")
  Page<Transaction> search(Long userId, String description, String transactionDate,
      String receivedDate, String amount, Pageable pageable);
}
