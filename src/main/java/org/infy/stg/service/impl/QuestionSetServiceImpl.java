package org.infy.stg.service.impl;

import org.infy.stg.service.QuestionSetService;
import org.infy.stg.domain.QuestionSet;
import org.infy.stg.repository.QuestionSetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionSet}.
 */
@Service
@Transactional
public class QuestionSetServiceImpl implements QuestionSetService {

    private final Logger log = LoggerFactory.getLogger(QuestionSetServiceImpl.class);

    private final QuestionSetRepository questionSetRepository;

    public QuestionSetServiceImpl(QuestionSetRepository questionSetRepository) {
        this.questionSetRepository = questionSetRepository;
    }

    @Override
    public QuestionSet save(QuestionSet questionSet) {
        log.debug("Request to save QuestionSet : {}", questionSet);
        return questionSetRepository.save(questionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionSet> findAll() {
        log.debug("Request to get all QuestionSets");
        return questionSetRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionSet> findOne(Long id) {
        log.debug("Request to get QuestionSet : {}", id);
        return questionSetRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionSet : {}", id);
        questionSetRepository.deleteById(id);
    }
}
