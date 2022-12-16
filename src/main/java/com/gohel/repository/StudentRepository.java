package com.gohel.repository;

import com.gohel.model.School;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.gohel.model.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Page<Student> findAllByUserAndSchoolIdNotNull(User user, Pageable pageable);

  Page<Student> findAllByUserAndSchoolIdNull(User user, Pageable pageable);

  Page<Student> findAllByUser(User user, Pageable pageable);

  Page<Student> findAllByUserAndNameLike(User user, String name, Pageable pageable);

  @Query("select sum(s.price) from Student s where s.school.id = ?1")
  Integer sumBySchoolId(Long schoolId);
  
  Integer countBySchoolId(Long schoolId);
}
