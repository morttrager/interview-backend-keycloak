package org.infy.stg.ext.service.impl;

import org.infy.stg.ext.service.InterviewQuestionService;
import org.infy.stg.domain.InterviewQuestion;
import org.infy.stg.ext.repository.InterviewQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InterviewQuestion}.
 */
@Service("extInterviewQuestionService")
@Transactional
@Primary
public class InterviewQuestionServiceImpl extends org.infy.stg.service.impl.InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionServiceImpl.class);

    private final InterviewQuestionRepository interviewQuestionRepository;

    public InterviewQuestionServiceImpl(InterviewQuestionRepository interviewQuestionRepository) {
        super(interviewQuestionRepository);
        this.interviewQuestionRepository = interviewQuestionRepository;
    }

    @Override
    public Optional<InterviewQuestion> currentQuestion(Long IntervieweeId) {
        log.debug("Request for current question of Interviewee id : {}", IntervieweeId);
        return interviewQuestionRepository.findByIntervieweeIdAndScoreIsNull(IntervieweeId);
    }

//    @Override
//    public void updateScore(Long interviewQuestionId, Float score){
//        log.debug("Updating score: {} for IntervieweeQuestion id: {}", score, interviewQuestionId);
//        interviewQuestionRepository.updateInterviewQuestionScore(interviewQuestionId, score);
//    }

    @Override
    public List<InterviewQuestion> questionsAsked(Long intervieweeId, Long interviewQuestionSetId){
        log.debug("Request for all questions asked to Interviewee {}", intervieweeId);
        return interviewQuestionRepository.findByIntervieweeIdAndInterviewqsId(intervieweeId, interviewQuestionSetId);
    }

    @Override
    public List<InterviewQuestion> questionsAsked( Long interviewQuestionSetId){
        log.debug("Request for all questions asked to Interviewee {}", interviewQuestionSetId);
        return interviewQuestionRepository.findByInterviewqsId(interviewQuestionSetId);
    }
}
