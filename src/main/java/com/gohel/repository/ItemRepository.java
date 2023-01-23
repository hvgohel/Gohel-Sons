package com.gohel.repository;

import com.gohel.model.Item;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
  Page<Item> findAllByUser(User user, Pageable pageable);

  @Query(
      "SELECT s FROM Item s WHERE s.user.id = ?1 AND (type LIKE ?2 OR description = ?3 OR address = ?4 OR purchaseDate = ?5 " +
          "OR quantity = ?6 OR amount = ?7)")
  Page<Item> search(Long userId, String type, String description, String address, String purchaseDate, String quantity,
      String amount, Pageable pageable);
}
