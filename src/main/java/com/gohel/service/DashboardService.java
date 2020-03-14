package com.gohel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import com.gohel.model.Student;
import com.gohel.repository.StudentRepository;

@Service
public class DashboardService extends AbstractService<Student, Long> {

  @Autowired
  private StudentRepository studentRepository;

  @Override
  protected JpaRepository<Student, Long> getRepository() {
    return studentRepository;
  }
}
