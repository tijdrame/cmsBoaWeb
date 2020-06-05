package com.boa.web.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ParamFiliale.
 */
@Entity
@Table(name = "param_filiale")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ParamFiliale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "param_seq_gen")
    @SequenceGenerator(name = "param_seq_gen", sequenceName = "param_id_seq", initialValue = 1, allocationSize = 1)
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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeFiliale() {
        return codeFiliale;
    }

    public ParamFiliale codeFiliale(String codeFiliale) {
        this.codeFiliale = codeFiliale;
        return this;
    }

    public void setCodeFiliale(String codeFiliale) {
        this.codeFiliale = codeFiliale;
    }

    public String getDesignationPays() {
        return designationPays;
    }

    public ParamFiliale designationPays(String designationPays) {
        this.designationPays = designationPays;
        return this;
    }

    public void setDesignationPays(String designationPays) {
        this.designationPays = designationPays;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public ParamFiliale endPoint(String endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Boolean isStatus() {
        return status;
    }

    public ParamFiliale status(Boolean status) {
        this.status = status;
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Instant getDateCre() {
        return dateCre;
    }

    public ParamFiliale dateCre(Instant dateCre) {
        this.dateCre = dateCre;
        return this;
    }

    public void setDateCre(Instant dateCre) {
        this.dateCre = dateCre;
    }

    public String getEndPointCompte() {
        return endPointCompte;
    }

    public ParamFiliale endPointCompte(String endPointCompte) {
        this.endPointCompte = endPointCompte;
        return this;
    }

    public void setEndPointCompte(String endPointCompte) {
        this.endPointCompte = endPointCompte;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        return 31;
    }

    @Override
    public String toString() {
        return "ParamFiliale{" +
            "id=" + getId() +
            ", codeFiliale='" + getCodeFiliale() + "'" +
            ", designationPays='" + getDesignationPays() + "'" +
            ", endPoint='" + getEndPoint() + "'" +
            ", status='" + isStatus() + "'" +
            ", dateCre='" + getDateCre() + "'" +
            ", endPointCompte='" + getEndPointCompte() + "'" +
            "}";
    }
}
