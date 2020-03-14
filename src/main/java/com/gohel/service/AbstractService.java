package com.gohel.service;

import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<T, ID> {
  private static final int PAGE_SIZE = 20;

  protected abstract JpaRepository<T, ID> getRepository();

  public Page<T> getList(Integer pageNumber) {
    return getRepository()
        .findAll(PageRequest.of(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "id"));
  }

  public List<T> getAll() {
    return getRepository().findAll();
  }

  public Page<T> getAll(Pageable pageable) {
    return getRepository().findAll(pageable);
  }

  public T save(T entity) {
    return getRepository().save(entity);
  }

  public T get(ID id) {
    T entity = getRepository().getOne(id);
    return entity;
  }

  public void delete(ID id) {
    try {
      getRepository().deleteById(id);
    } catch (EmptyResultDataAccessException e) {
    }
  }

  public void update(T entity) {
    getRepository().save(entity);
  }
}
