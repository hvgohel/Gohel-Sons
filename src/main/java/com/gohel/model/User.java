package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = "gohel_and_sons")
public class User extends AbstractModel<Long> {

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

  private String firstName;
  private String lastName;
  private String city;
  private String email;
}
