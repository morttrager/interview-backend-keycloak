package org.infy.stg.ext.service.impl;

import org.infy.stg.domain.Question;
import org.infy.stg.domain.QuestionSet;
import org.infy.stg.ext.repository.QuestionRepository;
import org.infy.stg.ext.repository.QuestionSetRepository;
import org.infy.stg.ext.service.QuestionService;
import org.infy.stg.ext.service.QuestionSetService;
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
@Service("extQuestionSetService")
@Primary
@Transactional
public class QuestionSetServiceImpl extends org.infy.stg.service.impl.QuestionSetServiceImpl implements QuestionSetService {

    private final Logger log = LoggerFactory.getLogger(QuestionSetServiceImpl.class);

    private final QuestionSetRepository questionSetRepository;

    public QuestionSetServiceImpl(QuestionSetRepository questionSetRepository) {
        super(questionSetRepository);
        this.questionSetRepository = questionSetRepository;
    }

    @Override
    public QuestionSet getQuestionSetByName(String name){
        log.debug("Request to get questionSet : {}",name);
        return questionSetRepository.findByName(name);
    }

}
