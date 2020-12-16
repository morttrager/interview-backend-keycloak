package org.infy.stg.ext.service.dto;

import org.infy.stg.config.Constants;
import org.infy.stg.domain.*;
import org.infy.stg.ext.repository.QuestionTagRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IntervieweeDataDTO {

    private InterviewQuestion interviewQuestion;
    private List<QuestionTag> questionTags;

    public IntervieweeDataDTO(){

    }

    public IntervieweeDataDTO(InterviewQuestion interviewQuestion, List<QuestionTag> questionTags) {
        this.interviewQuestion = interviewQuestion;
        this.questionTags = questionTags;

    }

    public void seInterviewQuestion(InterviewQuestion interviewQuestion) {
        this.interviewQuestion = interviewQuestion;
    }
    public void seQuestionTags(List<QuestionTag> questionTags) {
        this.questionTags = questionTags;
    }

    public List<QuestionTag> getQuestionTags() {
        return questionTags;
    }
    public InterviewQuestion getInterviewQuestion() {
        return interviewQuestion;
    }

}




