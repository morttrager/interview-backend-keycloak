package org.infy.stg.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A QuestionSet.
 */
@Entity
@Table(name = "question_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Size(max = 8192)
    @Column(name = "intro", length = 8192)
    private String intro;

    @Size(max = 8192)
    @Column(name = "outro", length = 8192)
    private String outro;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public QuestionSet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public QuestionSet intro(String intro) {
        this.intro = intro;
        return this;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getOutro() {
        return outro;
    }

    public QuestionSet outro(String outro) {
        this.outro = outro;
        return this;
    }

    public void setOutro(String outro) {
        this.outro = outro;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionSet)) {
            return false;
        }
        return id != null && id.equals(((QuestionSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionSet{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", intro='" + getIntro() + "'" +
            ", outro='" + getOutro() + "'" +
            "}";
    }
}
