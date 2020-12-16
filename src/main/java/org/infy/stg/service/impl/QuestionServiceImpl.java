package org.infy.stg.service.impl;

import org.infy.stg.service.QuestionService;
import org.infy.stg.domain.Question;
import org.infy.stg.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question save(Question question) {
        log.debug("Request to save Question : {}", question);
        return questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Question> findAll() {
        log.debug("Request to get all Questions");
        return questionRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Question> findOne(Long id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }
}
