package com.gohel.service;

import lombok.RequiredArgsConstructor;
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
        .findAllByUserAndSchoolIdNotNull(userService.currentUser(),
                PageRequest.of(pageNumber - 1, 5, Sort.Direction.DESC, "id"));
  }

  public Page<Student> searchStudent(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository
            .searchStudent(userService.currentUser().getId(), searchLike, search, search, searchLike, search,
                    PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "id"));
  }

  public Page<Student> getCustomers(Integer pageNumber) {
    return studentRepository.findAllByUserAndSchoolIdNull(userService.currentUser(),
        PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "deliveryDate"));
  }

  public Page<Student> searchCustomer(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository.searchCustomer(userService.currentUser().getId(),
            searchLike, searchLike, search, searchLike, search,
            PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "delivery_date"));
  }

  public Page<Student> getAllByUser(Integer pageNumber) {
    return studentRepository.findAllByUser(userService.currentUser(),
            PageRequest.of(pageNumber - 1, 10, Sort.Direction.ASC, "deliveryDate"));
  }

  public Page<Student> search(Integer pageNumber, String search) {
    String searchLike = "%" + search + "%";
    return studentRepository.search(userService.currentUser().getId(), searchLike, search, search, searchLike,
            PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "deliveryDate"));
  }

  public Integer getTotalAmount(Long schoolId) {
    return studentRepository.sumBySchoolId(schoolId);
  }

  public Integer getTotalStudent(Long schoolId) {
    return studentRepository.countBySchoolId(schoolId);
  }
}
