package org.infy.stg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Interviewee.
 */
@Entity
@Table(name = "interviewee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Interviewee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "remaining")
    private Integer remaining;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public Interviewee candidateId(String candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getRemaining() {
        return remaining;
    }

    public Interviewee remaining(Integer remaining) {
        this.remaining = remaining;
        return this;
    }

    public void setRemaining(Integer remaining) {
        this.remaining = remaining;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Interviewee)) {
            return false;
        }
        return id != null && id.equals(((Interviewee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Interviewee{" +
            "id=" + getId() +
            ", candidateId='" + getCandidateId() + "'" +
            ", remaining=" + getRemaining() +
            "}";
    }
}
