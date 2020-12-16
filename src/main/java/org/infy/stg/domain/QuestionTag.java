package org.infy.stg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A QuestionTag.
 */
@Entity
@Table(name = "question_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "questionTags", allowSetters = true)
    private QTag qtag;

    @ManyToOne
    @JsonIgnoreProperties(value = "questionTags", allowSetters = true)
    private Question question;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QTag getQtag() {
        return qtag;
    }

    public QuestionTag qtag(QTag qTag) {
        this.qtag = qTag;
        return this;
    }

    public void setQtag(QTag qTag) {
        this.qtag = qTag;
    }

    public Question getQuestion() {
        return question;
    }

    public QuestionTag question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionTag)) {
            return false;
        }
        return id != null && id.equals(((QuestionTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTag{" +
            "id=" + getId() +
            "}";
    }
}
