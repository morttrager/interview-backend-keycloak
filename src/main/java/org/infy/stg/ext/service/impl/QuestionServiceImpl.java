package org.infy.stg.ext.service.impl;

import org.infy.stg.ext.service.QuestionService;
import org.infy.stg.domain.Question;
import org.infy.stg.ext.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service("extQuestionService")
@Primary
@Transactional
public class QuestionServiceImpl extends org.infy.stg.service.impl.QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        super(questionRepository);
        this.questionRepository = questionRepository;
    }

}
