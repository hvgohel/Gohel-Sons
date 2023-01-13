package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(schema = "gohel_and_sons")
public class Item extends AbstractModel<Long> {

  @Column(nullable = false)
  private String type;

  private String description;

  private String address;

  private String purchaseDate;

  private String quantity;

  private String comment;

  private String status;

  private Double amount;

  private String receipt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
