package com.boa.web.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Institution.
 */
@Entity
@Table(name = "institution")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Institution implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "institution_id")
  private String institutionId;

  @Column(name = "pays")
  private String pays;

  @Column(name = "code")
  private String code;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Institution id(Long id) {
    this.id = id;
    return this;
  }

  public String getInstitutionId() {
    return this.institutionId;
  }

  public Institution institutionId(String institutionId) {
    this.institutionId = institutionId;
    return this;
  }

  public void setInstitutionId(String institutionId) {
    this.institutionId = institutionId;
  }

  public String getPays() {
    return this.pays;
  }

  public Institution pays(String pays) {
    this.pays = pays;
    return this;
  }

  public void setPays(String pays) {
    this.pays = pays;
  }

  public String getCode() {
    return this.code;
  }

  public Institution code(String code) {
    this.code = code;
    return this;
  }

  public void setCode(String code) {
    this.code = code;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Institution)) {
      return false;
    }
    return id != null && id.equals(((Institution) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "Institution{" +
            "id=" + getId() +
            ", institutionId='" + getInstitutionId() + "'" +
            ", pays='" + getPays() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
