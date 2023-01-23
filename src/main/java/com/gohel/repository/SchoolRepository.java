package com.gohel.repository;

import com.gohel.model.School;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
  Page<School> findAllByUser(User user, Pageable pageable);

  Page<School> findAllByUserAndNameLike(User user, String name, Pageable pageable);

  List<School> findAllByUser(User user);
}
