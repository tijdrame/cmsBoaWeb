package com.boa.web.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParamIdentifier.
 */
@Entity
@Table(name = "param_identifier")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParamIdentifier implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @NotNull
  @Column(name = "code", nullable = false)
  private Integer code;

  @NotNull
  @Column(name = "libelle", nullable = false)
  private String libelle;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ParamIdentifier id(Long id) {
    this.id = id;
    return this;
  }

  public Integer getCode() {
    return this.code;
  }

  public ParamIdentifier code(Integer code) {
    this.code = code;
    return this;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getLibelle() {
    return this.libelle;
  }

  public ParamIdentifier libelle(String libelle) {
    this.libelle = libelle;
    return this;
  }

  public void setLibelle(String libelle) {
    this.libelle = libelle;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParamIdentifier)) {
      return false;
    }
    return id != null && id.equals(((ParamIdentifier) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ParamIdentifier{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", libelle='" + getLibelle() + "'" +
            "}";
    }
}
