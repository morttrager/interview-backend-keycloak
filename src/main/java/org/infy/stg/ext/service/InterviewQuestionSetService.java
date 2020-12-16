package org.infy.stg.ext.service;

import org.infy.stg.domain.InterviewQuestionSet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InterviewQuestionSet}.
 */
public interface InterviewQuestionSetService extends org.infy.stg.service.InterviewQuestionSetService{

    List<InterviewQuestionSet> findByIntervieweeId(Long intervieweeId);
    List<InterviewQuestionSet> findByIntervieweeIdAndActive(Long intervieweeId,Boolean active);
    List<InterviewQuestionSet> findByQuestionSetName(String questionSet);
    List<InterviewQuestionSet> findByIntervieweeIdAndQuestionsetName(Long intervieweeId, String questionSetName);
}
