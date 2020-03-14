package com.gohel.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbstractModel<ID> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private ID id;

  @CreationTimestamp
  @Column(updatable = false)
  private Date created;

  @UpdateTimestamp
  private Date updated;

  public ID getId() {
    return this.id;
  }

  public void setId(ID id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    AbstractModel<?> other = (AbstractModel<?>) obj;
    if (id == null) {
      return other.id == null;
    } else
      return id.equals(other.id);
  }
}
