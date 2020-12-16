package org.infy.stg.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InterviewQuestion.
 */
@Entity
@Table(name = "interview_question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class InterviewQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "score")
    private Float score;

    @Size(max = 8192)
    @Column(name = "respone", length = 8192)
    private String respone;

    @Column(name = "time")
    private Instant time;

    @ManyToOne
    @JsonIgnoreProperties(value = "interviewQuestions", allowSetters = true)
    private Interviewee interviewee;

    @ManyToOne
    @JsonIgnoreProperties(value = "interviewQuestions", allowSetters = true)
    private Question question;

    @ManyToOne
    @JsonIgnoreProperties(value = "interviewQuestions", allowSetters = true)
    private InterviewQuestionSet interviewqs;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getScore() {
        return score;
    }

    public InterviewQuestion score(Float score) {
        this.score = score;
        return this;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getRespone() {
        return respone;
    }

    public InterviewQuestion respone(String respone) {
        this.respone = respone;
        return this;
    }

    public void setRespone(String respone) {
        this.respone = respone;
    }

    public Instant getTime() {
        return time;
    }

    public InterviewQuestion time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Interviewee getInterviewee() {
        return interviewee;
    }

    public InterviewQuestion interviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
        return this;
    }

    public void setInterviewee(Interviewee interviewee) {
        this.interviewee = interviewee;
    }

    public Question getQuestion() {
        return question;
    }

    public InterviewQuestion question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public InterviewQuestionSet getInterviewqs() {
        return interviewqs;
    }

    public InterviewQuestion interviewqs(InterviewQuestionSet interviewQuestionSet) {
        this.interviewqs = interviewQuestionSet;
        return this;
    }

    public void setInterviewqs(InterviewQuestionSet interviewQuestionSet) {
        this.interviewqs = interviewQuestionSet;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InterviewQuestion)) {
            return false;
        }
        return id != null && id.equals(((InterviewQuestion) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InterviewQuestion{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", respone='" + getRespone() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
