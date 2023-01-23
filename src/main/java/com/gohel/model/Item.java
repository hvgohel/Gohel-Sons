package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Item extends AbstractModel<Long> {
  @Column(nullable = false)
  private String type;
  private String description;
  private String address;
  private String purchaseDate;
  private String quantity;
  private String amount;

  @Column(columnDefinition = "LONGTEXT")
  private String receipt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
