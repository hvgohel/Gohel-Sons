package com.gohel.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Transaction extends AbstractModel<Long> {
  private String date;
  private String receivedDate;
  private String description;
  private String amount;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
