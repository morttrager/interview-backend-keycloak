package org.infy.stg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A QuestionSetTag.
 */
@Entity
@Table(name = "question_set_tag")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionSetTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "q_count")
    private Integer qCount;

    @ManyToOne
    @JsonIgnoreProperties(value = "questionSetTags", allowSetters = true)
    private QuestionSet questionset;

    @ManyToOne
    @JsonIgnoreProperties(value = "questionSetTags", allowSetters = true)
    private QTag qtag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getqCount() {
        return qCount;
    }

    public QuestionSetTag qCount(Integer qCount) {
        this.qCount = qCount;
        return this;
    }

    public void setqCount(Integer qCount) {
        this.qCount = qCount;
    }

    public QuestionSet getQuestionset() {
        return questionset;
    }

    public QuestionSetTag questionset(QuestionSet questionSet) {
        this.questionset = questionSet;
        return this;
    }

    public void setQuestionset(QuestionSet questionSet) {
        this.questionset = questionSet;
    }

    public QTag getQtag() {
        return qtag;
    }

    public QuestionSetTag qtag(QTag qTag) {
        this.qtag = qTag;
        return this;
    }

    public void setQtag(QTag qTag) {
        this.qtag = qTag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionSetTag)) {
            return false;
        }
        return id != null && id.equals(((QuestionSetTag) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionSetTag{" +
            "id=" + getId() +
            ", qCount=" + getqCount() +
            "}";
    }
}
