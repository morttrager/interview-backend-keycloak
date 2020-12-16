package org.infy.stg.service.impl;

import org.infy.stg.service.InterviewQuestionSetService;
import org.infy.stg.domain.InterviewQuestionSet;
import org.infy.stg.repository.InterviewQuestionSetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InterviewQuestionSet}.
 */
@Service
@Transactional
public class InterviewQuestionSetServiceImpl implements InterviewQuestionSetService {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionSetServiceImpl.class);

    private final InterviewQuestionSetRepository interviewQuestionSetRepository;

    public InterviewQuestionSetServiceImpl(InterviewQuestionSetRepository interviewQuestionSetRepository) {
        this.interviewQuestionSetRepository = interviewQuestionSetRepository;
    }

    @Override
    public InterviewQuestionSet save(InterviewQuestionSet interviewQuestionSet) {
        log.debug("Request to save InterviewQuestionSet : {}", interviewQuestionSet);
        return interviewQuestionSetRepository.save(interviewQuestionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewQuestionSet> findAll() {
        log.debug("Request to get all InterviewQuestionSets");
        return interviewQuestionSetRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InterviewQuestionSet> findOne(Long id) {
        log.debug("Request to get InterviewQuestionSet : {}", id);
        return interviewQuestionSetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterviewQuestionSet : {}", id);
        interviewQuestionSetRepository.deleteById(id);
    }
}
