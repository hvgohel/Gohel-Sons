package com.gohel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gohel.model.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
}
