package com.gohel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.gohel.model.School;
import com.gohel.repository.SchoolRepository;

@Service
public class SchoolService extends AbstractService<School, Long> {

  @Autowired
  private SchoolRepository schoolRepository;

  @Override
  protected JpaRepository<School, Long> getRepository() {
    return schoolRepository;
  }
}
