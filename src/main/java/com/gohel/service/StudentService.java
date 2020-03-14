package com.gohel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.gohel.model.Student;
import com.gohel.repository.StudentRepository;

@Service
public class StudentService extends AbstractService<Student, Long> {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  protected JpaRepository<Student, Long> getRepository() {
    return studentRepository;
  }

  public Page<Student> getStudents(Integer pageNumber) {
    return studentRepository
        .findAllBySchoolIdNotNull(PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "id"));
  }

  public Page<Student> getCustomers(Integer pageNumber) {
    return studentRepository.findAllBySchoolIdNull(
        PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "deliveryDate"));
  }

  public Integer getTotalAmount(Long schoolId) {
    return studentRepository.sumBySchoolId(schoolId);
  }
  
  public Integer getTotalStudent(Long schoolId) {
    return studentRepository.countBySchoolId(schoolId);
  }
}
