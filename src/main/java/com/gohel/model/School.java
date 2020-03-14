package com.gohel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = "gohel_and_sons")
public class School extends AbstractModel<Long> {

  @Column(nullable = false)
  private String name;

  private String address;

  @Column(nullable = false, unique = true)
  private String email;

  private String deliveryDate;
  
  @Transient
  private Integer totalAmount;
  
  @Transient
  private Integer totalStudent;
}
