package com.gohel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.gohel.model.Student;
import com.gohel.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService extends AbstractService<Student, Long> {

  private final StudentRepository studentRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<Student, Long> getRepository() {
    return studentRepository;
  }

  public Page<Student> getStudents(Integer pageNumber) {
    return studentRepository
        .findAllByUserAndSchoolIdNotNull(userService.currentUser(), PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "id"));
  }

  public Page<Student> getCustomers(Integer pageNumber) {
    return studentRepository.findAllByUserAndSchoolIdNull(userService.currentUser(),
        PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "deliveryDate"));
  }

  public Page<Student> getAllByUser(Integer pageNumber) {
    return studentRepository.findAllByUser(userService.currentUser(),
            PageRequest.of(pageNumber - 1, 20, Sort.Direction.DESC, "deliveryDate"));
  }

  public Page<Student> getAllByUserAndName(Integer pageNumber, String name) {
    return studentRepository.findAllByUserAndNameLike(userService.currentUser(), "%" + name + "%",
            PageRequest.of(pageNumber - 1, 20, Sort.Direction.ASC, "deliveryDate"));
  }

  public Integer getTotalAmount(Long schoolId) {
    return studentRepository.sumBySchoolId(schoolId);
  }
  
  public Integer getTotalStudent(Long schoolId) {
    return studentRepository.countBySchoolId(schoolId);
  }
}
