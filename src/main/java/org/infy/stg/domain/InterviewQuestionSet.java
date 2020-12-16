package org.infy.stg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InterviewQuestionSet.
 */
@Entity
@Table(name = "interview_question_set")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InterviewQuestionSet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "abandoned")
    private Boolean abandoned;

    @Column(name = "net_score")
    private Float netScore;

    @Column(name = "net_questions")
    private Integer netQuestions;

    @Column(name = "net_weight")
    private Integer netWeight;

    @Column(name = "time")
    private Instant time;

    @ManyToOne
    @JsonIgnoreProperties(value = "interviewQuestionSets", allowSetters = true)
    private QuestionSet questionset;

    @ManyToOne
    @JsonIgnoreProperties(value = "interviewQuestionSets", allowSetters = true)
    private Interviewee interviewee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public InterviewQuestionSet active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean isAbandoned() {
        return abandoned;
    }

    public InterviewQuestionSet abandoned(Boolean abandoned) {
        this.abandoned = abandoned;
        return this;
    }

    public void setAbandoned(Boolean abandoned) {
        this.abandoned = abandoned;
    }

    public Float getNetScore() {
        return netScore;
    }

    public InterviewQuestionSet netScore(Float netScore) {
        this.netScore = netScore;
        return this;
    }

    public void setNetScore(Float netScore) {
        this.netScore = netScore;
    }

    public Integer getNetQuestions() {
        return netQuestions;
    }

    public InterviewQuestionSet netQuestions(Integer netQuestions) {
        this.netQuestions = netQuestions;
        return this;
    }

    public void setNetQuestions(Integer netQuestions) {
        this.netQuestions = netQuestions;
    }

    public Integer getNetWeight() {
        return netWeight;
    }

    public InterviewQuestionSet netWeight(Integer netWeight) {
        this.netWeight = netWeight;
        return this;
    }

    public void setNetWeight(Integer netWeight) {
        this.netWeight = netWeight;
    }

    public Instant getTime() {
        return time;
    }

    public InterviewQuestionSet time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public QuestionSet getQuestionset() {
        return questionset;
    }

    public InterviewQuestionSet questionset(QuestionSet questionSet) {
        this.questionset = questionSet;
        return this;
    }

    public void setQuestionset(QuestionSet questionSet) {
        this.questionset = questionSet;
    }

    public Interviewee getInterviewee() {
        return interviewee;
    }

    public InterviewQuestionSet interviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
        return this;
    }

    public void setInterviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewQuestionSet)) {
            return false;
        }
        return id != null && id.equals(((InterviewQuestionSet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewQuestionSet{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", abandoned='" + isAbandoned() + "'" +
            ", netScore=" + getNetScore() +
            ", netQuestions=" + getNetQuestions() +
            ", netWeight=" + getNetWeight() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
