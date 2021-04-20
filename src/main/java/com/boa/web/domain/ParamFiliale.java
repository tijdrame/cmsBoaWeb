package com.boa.web.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ParamFiliale.
 */
@Entity
@Table(name = "param_filiale")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ParamFiliale implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "code_filiale")
  private String codeFiliale;

  @Column(name = "designation_pays")
  private String designationPays;

  @Column(name = "end_point")
  private String endPoint;

  @Column(name = "status")
  private Boolean status;

  @Column(name = "date_cre")
  private Instant dateCre;

  @Column(name = "end_point_compte")
  private String endPointCompte;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ParamFiliale id(Long id) {
    this.id = id;
    return this;
  }

  public String getCodeFiliale() {
    return this.codeFiliale;
  }

  public ParamFiliale codeFiliale(String codeFiliale) {
    this.codeFiliale = codeFiliale;
    return this;
  }

  public void setCodeFiliale(String codeFiliale) {
    this.codeFiliale = codeFiliale;
  }

  public String getDesignationPays() {
    return this.designationPays;
  }

  public ParamFiliale designationPays(String designationPays) {
    this.designationPays = designationPays;
    return this;
  }

  public void setDesignationPays(String designationPays) {
    this.designationPays = designationPays;
  }

  public String getEndPoint() {
    return this.endPoint;
  }

  public ParamFiliale endPoint(String endPoint) {
    this.endPoint = endPoint;
    return this;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public Boolean getStatus() {
    return this.status;
  }

  public ParamFiliale status(Boolean status) {
    this.status = status;
    return this;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Instant getDateCre() {
    return this.dateCre;
  }

  public ParamFiliale dateCre(Instant dateCre) {
    this.dateCre = dateCre;
    return this;
  }

  public void setDateCre(Instant dateCre) {
    this.dateCre = dateCre;
  }

  public String getEndPointCompte() {
    return this.endPointCompte;
  }

  public ParamFiliale endPointCompte(String endPointCompte) {
    this.endPointCompte = endPointCompte;
    return this;
  }

  public void setEndPointCompte(String endPointCompte) {
    this.endPointCompte = endPointCompte;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ParamFiliale)) {
      return false;
    }
    return id != null && id.equals(((ParamFiliale) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "ParamFiliale{" +
            "id=" + getId() +
            ", codeFiliale='" + getCodeFiliale() + "'" +
            ", designationPays='" + getDesignationPays() + "'" +
            ", endPoint='" + getEndPoint() + "'" +
            ", status='" + getStatus() + "'" +
            ", dateCre='" + getDateCre() + "'" +
            ", endPointCompte='" + getEndPointCompte() + "'" +
            "}";
    }
}
