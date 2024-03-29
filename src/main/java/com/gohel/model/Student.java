package com.gohel.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
public class Student extends AbstractModel<Long> {

  @Column(nullable = false)
  private String name;

  private String classRoom;
  private String deliveryDate;
  private String gender;
  private String address;
  private String mobile;
  private Integer noofShirtKurti;
  private Integer noofPentSalwar;
  private Integer price;
  private String payment;
  private String invoice;

  // kurti
  private String kurtiWaist;
  private String kurtiSeat;

  // pent
  private String pentLength;
  private String waist;
  private String seat;
  private String hip;
  private String knee;
  private String ankleRound;
  private String inseam;
  private String crotch;

  // shirt
  private String collar;
  private String chest;
  private String shoulder;
  private String sleeve;
  private String shirtLength;
  private String culf;
  private String front;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  private School school;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
}
