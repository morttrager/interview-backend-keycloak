package org.infy.stg.service.impl;

import org.infy.stg.service.QuestionTagService;
import org.infy.stg.domain.QuestionTag;
import org.infy.stg.repository.QuestionTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionTag}.
 */
@Service
@Transactional
public class QuestionTagServiceImpl implements QuestionTagService {

    private final Logger log = LoggerFactory.getLogger(QuestionTagServiceImpl.class);

    private final QuestionTagRepository questionTagRepository;

    public QuestionTagServiceImpl(QuestionTagRepository questionTagRepository) {
        this.questionTagRepository = questionTagRepository;
    }

    @Override
    public QuestionTag save(QuestionTag questionTag) {
        log.debug("Request to save QuestionTag : {}", questionTag);
        return questionTagRepository.save(questionTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionTag> findAll() {
        log.debug("Request to get all QuestionTags");
        return questionTagRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionTag> findOne(Long id) {
        log.debug("Request to get QuestionTag : {}", id);
        return questionTagRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionTag : {}", id);
        questionTagRepository.deleteById(id);
    }
}
