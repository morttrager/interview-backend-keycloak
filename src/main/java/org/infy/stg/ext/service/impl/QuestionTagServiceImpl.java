package org.infy.stg.ext.service.impl;

import org.infy.stg.ext.service.QuestionTagService;
import org.infy.stg.domain.QuestionTag;
import org.infy.stg.ext.repository.QuestionTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * Service Implementation for managing {@link QuestionTag}.
 */
@Service("extQuestionTagService")
@Primary
@Transactional
public class QuestionTagServiceImpl extends org.infy.stg.service.impl.QuestionTagServiceImpl implements QuestionTagService {

    private final Logger log = LoggerFactory.getLogger(QuestionTagServiceImpl.class);

    private final QuestionTagRepository questionTagRepository;



    public QuestionTagServiceImpl(QuestionTagRepository questionTagRepository) {
        super(questionTagRepository);
        this.questionTagRepository = questionTagRepository;
    }

    @Override
    public List<QuestionTag> findQuestionsForTopic(String tagName) {
        log.debug("Request for question from topic : {}", tagName);
        return questionTagRepository.findByQtagName(tagName);
    }

    @Override
    public List<QuestionTag> getQTags(Long questionId) {
        log.debug("Request for tag of question : {}", questionId);
        return questionTagRepository.findByQuestionId(questionId);
    }


    @Override
    public List<QuestionTag> findByQtagIdAndQuestionWeight(Long tagId, Integer weight){
        return  questionTagRepository.findByQtagIdAndQuestionWeight(tagId,weight);
    }

}
