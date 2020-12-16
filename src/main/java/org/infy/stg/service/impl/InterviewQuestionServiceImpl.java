package org.infy.stg.service.impl;

import org.infy.stg.service.InterviewQuestionService;
import org.infy.stg.domain.InterviewQuestion;
import org.infy.stg.repository.InterviewQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InterviewQuestion}.
 */
@Service
@Transactional
public class InterviewQuestionServiceImpl implements InterviewQuestionService {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionServiceImpl.class);

    private final InterviewQuestionRepository interviewQuestionRepository;

    public InterviewQuestionServiceImpl(InterviewQuestionRepository interviewQuestionRepository) {
        this.interviewQuestionRepository = interviewQuestionRepository;
    }

    @Override
    public InterviewQuestion save(InterviewQuestion interviewQuestion) {
        log.debug("Request to save InterviewQuestion : {}", interviewQuestion);
        return interviewQuestionRepository.save(interviewQuestion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewQuestion> findAll() {
        log.debug("Request to get all InterviewQuestions");
        return interviewQuestionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InterviewQuestion> findOne(Long id) {
        log.debug("Request to get InterviewQuestion : {}", id);
        return interviewQuestionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterviewQuestion : {}", id);
        interviewQuestionRepository.deleteById(id);
    }
}
