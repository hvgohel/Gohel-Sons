package com.gohel.repository;

import com.gohel.model.Item;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
  Page<Item> findAllByUser(User user, Pageable pageable);

  Page<Item> findAllByUserAndTypeLike(User user, String type, Pageable pageable);

  List<Item> findAllByUser(User user);
}
