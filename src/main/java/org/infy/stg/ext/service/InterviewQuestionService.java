package org.infy.stg.ext.service;

import org.infy.stg.domain.InterviewQuestion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InterviewQuestion}.
 */
public interface InterviewQuestionService extends org.infy.stg.service.InterviewQuestionService{

    Optional<InterviewQuestion> currentQuestion(Long IntervieweeId);

//    void updateScore(Long interviewQuestionId, Float score);

    List<InterviewQuestion> questionsAsked(Long intervieweeId, Long interviewQuestionSetId);

    List<InterviewQuestion> questionsAsked(Long interviewQuestionSetId);
}
