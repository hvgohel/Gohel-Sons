package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class User extends AbstractModel<Long> {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private String firstName;
  private String middleName;
  private String lastName;
  private String city;
  private String email;
  private String mobile;

  @Column(columnDefinition = "LONGTEXT")
  private String profile;
  private String companyName;
  private String companyAddress;
}
