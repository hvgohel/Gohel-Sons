package com.gohel.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.gohel.model.School;
import com.gohel.repository.SchoolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService extends AbstractService<School, Long> {

  private final SchoolRepository schoolRepository;
  private final UserService userService;

  @Override
  protected JpaRepository<School, Long> getRepository() {
    return schoolRepository;
  }

  public Page<School> getSchools(Integer pageNumber) {
    return schoolRepository
            .findAllByUser(userService.currentUser(), PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "id"));
  }

  public Page<School> getSchoolsBySearch(Integer pageNumber, String search) {
    return schoolRepository
            .findAllByUserAndNameLike(userService.currentUser(), "%" + search + "%",
                    PageRequest.of(pageNumber - 1, 10, Sort.Direction.DESC, "id"));
  }

  public List<School> getAllSchool() {
    return schoolRepository.findAllByUser(userService.currentUser());
  }
}
