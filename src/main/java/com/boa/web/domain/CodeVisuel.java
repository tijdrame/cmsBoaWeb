package com.boa.web.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CodeVisuel.
 */
@Entity
@Table(name = "code_visuel")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CodeVisuel implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "code")
  private String code;

  @Column(name = "sous_bin")
  private String sousBin;

  @Column(name = "taille")
  private Integer taille;

  @Column(name = "range_low")
  private Long rangeLow;

  @Column(name = "range_high")
  private Long rangeHigh;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public CodeVisuel id(Long id) {
    this.id = id;
    return this;
  }

  public String getCode() {
    return this.code;
  }

  public CodeVisuel code(String code) {
    this.code = code;
    return this;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getSousBin() {
    return this.sousBin;
  }

  public CodeVisuel sousBin(String sousBin) {
    this.sousBin = sousBin;
    return this;
  }

  public void setSousBin(String sousBin) {
    this.sousBin = sousBin;
  }

  public Integer getTaille() {
    return this.taille;
  }

  public CodeVisuel taille(Integer taille) {
    this.taille = taille;
    return this;
  }

  public void setTaille(Integer taille) {
    this.taille = taille;
  }

  public Long getRangeLow() {
    return this.rangeLow;
  }

  public CodeVisuel rangeLow(Long rangeLow) {
    this.rangeLow = rangeLow;
    return this;
  }

  public void setRangeLow(Long rangeLow) {
    this.rangeLow = rangeLow;
  }

  public Long getRangeHigh() {
    return this.rangeHigh;
  }

  public CodeVisuel rangeHigh(Long rangeHigh) {
    this.rangeHigh = rangeHigh;
    return this;
  }

  public void setRangeHigh(Long rangeHigh) {
    this.rangeHigh = rangeHigh;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof CodeVisuel)) {
      return false;
    }
    return id != null && id.equals(((CodeVisuel) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "CodeVisuel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", sousBin='" + getSousBin() + "'" +
            ", taille=" + getTaille() +
            ", rangeLow=" + getRangeLow() +
            ", rangeHigh=" + getRangeHigh() +
            "}";
    }
}
