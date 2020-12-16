package org.infy.stg.ext.service.impl;

import org.infy.stg.domain.QuestionSetTag;
import org.infy.stg.ext.repository.QuestionSetTagRepository;
import org.infy.stg.ext.service.QuestionSetTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionSetTag}.
 */
@Service("extQuestionSetTagServiceImpl")
@Transactional
@Primary
public class QuestionSetTagServiceImpl extends org.infy.stg.service.impl.QuestionSetTagServiceImpl implements QuestionSetTagService {

    private final Logger log = LoggerFactory.getLogger(QuestionSetTagServiceImpl.class);

    private final QuestionSetTagRepository questionSetTagRepository;

    public QuestionSetTagServiceImpl(QuestionSetTagRepository questionSetTagRepository) {
        super(questionSetTagRepository);
        this.questionSetTagRepository = questionSetTagRepository;
    }

    @Override
    public List<QuestionSetTag> findByQuestionsetId(Long questionsetId){
        return questionSetTagRepository.findByQuestionsetId(questionsetId);
    }

    @Override
    public List<QuestionSetTag> findByQuestionsetName(String questionSetName){
        return questionSetTagRepository.findByQuestionsetName(questionSetName);
    }
}
