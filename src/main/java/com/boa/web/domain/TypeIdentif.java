package com.boa.web.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TypeIdentif.
 */
@Entity
@Table(name = "type_identif")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TypeIdentif implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
  @SequenceGenerator(name = "sequenceGenerator")
  private Long id;

  @Column(name = "identifier")
  private String identifier;

  @Column(name = "default_identifier")
  private String defaultIdentifier;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public TypeIdentif id(Long id) {
    this.id = id;
    return this;
  }

  public String getIdentifier() {
    return this.identifier;
  }

  public TypeIdentif identifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getDefaultIdentifier() {
    return this.defaultIdentifier;
  }

  public TypeIdentif defaultIdentifier(String defaultIdentifier) {
    this.defaultIdentifier = defaultIdentifier;
    return this;
  }

  public void setDefaultIdentifier(String defaultIdentifier) {
    this.defaultIdentifier = defaultIdentifier;
  }

  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TypeIdentif)) {
      return false;
    }
    return id != null && id.equals(((TypeIdentif) o).id);
  }

  @Override
  public int hashCode() {
    // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
    return getClass().hashCode();
  }

  // prettier-ignore
    @Override
    public String toString() {
        return "TypeIdentif{" +
            "id=" + getId() +
            ", identifier='" + getIdentifier() + "'" +
            ", defaultIdentifier='" + getDefaultIdentifier() + "'" +
            "}";
    }
}
