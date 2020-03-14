package com.gohel.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gohel.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Page<Student> findAllBySchoolIdNull(Pageable pageable);

  Page<Student> findAllBySchoolIdNotNull(Pageable pageable);

  @Query("select sum(s.price) from Student s where s.school.id = ?1")
  Integer sumBySchoolId(Long schoolId);
  
  Integer countBySchoolId(Long schoolId);
}
