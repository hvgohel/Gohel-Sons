package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class School extends AbstractModel<Long> {

  @Column(nullable = false)
  private String name;

  private String address;

  @Column(nullable = false)
  private String email;

  private String deliveryDate;
  private String invoice;

  @Transient
  private Integer totalAmount;

  @Transient
  private Integer totalStudent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
