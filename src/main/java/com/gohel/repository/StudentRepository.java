package com.gohel.repository;

import com.gohel.model.Student;
import com.gohel.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Page<Student> findAllByUserAndSchoolIdNotNull(User user, Pageable pageable);

  Page<Student> findAllByUserAndSchoolIdNull(User user, Pageable pageable);

  Page<Student> findAllByUser(User user, Pageable pageable);

  @Query(
      "SELECT s FROM Student s WHERE s.user.id = ?1 AND (name LIKE ?2 OR payment = ?3 OR gender = ?4 OR mobile LIKE ?5)")
  Page<Student> search(Long userId, String name, String payment, String gender, String mobile,
      Pageable pageable);

  @Query(
      value = "SELECT * FROM student WHERE user_id = ?1 AND school_id is null AND " + "(mobile LIKE ?2 OR name LIKE ?3 OR payment = ?4 OR address LIKE ?5 OR gender = ?6)",
      nativeQuery = true)
  Page<Student> searchCustomer(Long userId, String mobile, String name, String payment,
      String address, String gender, Pageable pageable);

  @Query(
      "SELECT s FROM Student s WHERE s.user.id = ?1 AND s.school.id is not null AND " + "(name LIKE ?2 OR payment = ?3 OR gender = ?4 OR s.school.name LIKE ?5 OR classRoom = ?6)")
  Page<Student> searchStudent(Long userId, String name, String payment, String gender,
      String schoolName, String standard, Pageable pageable);

  @Query("select sum(s.price) from Student s where s.school.id = ?1")
  Integer sumBySchoolId(Long schoolId);

  Integer countBySchoolId(Long schoolId);
  List<Student> findAllBySchoolId(Long schoolId);
}
