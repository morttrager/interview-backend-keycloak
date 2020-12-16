package org.infy.stg.service.impl;

import org.infy.stg.service.QuestionSetTagService;
import org.infy.stg.domain.QuestionSetTag;
import org.infy.stg.repository.QuestionSetTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QuestionSetTag}.
 */
@Service
@Transactional
public class QuestionSetTagServiceImpl implements QuestionSetTagService {

    private final Logger log = LoggerFactory.getLogger(QuestionSetTagServiceImpl.class);

    private final QuestionSetTagRepository questionSetTagRepository;

    public QuestionSetTagServiceImpl(QuestionSetTagRepository questionSetTagRepository) {
        this.questionSetTagRepository = questionSetTagRepository;
    }

    @Override
    public QuestionSetTag save(QuestionSetTag questionSetTag) {
        log.debug("Request to save QuestionSetTag : {}", questionSetTag);
        return questionSetTagRepository.save(questionSetTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionSetTag> findAll() {
        log.debug("Request to get all QuestionSetTags");
        return questionSetTagRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QuestionSetTag> findOne(Long id) {
        log.debug("Request to get QuestionSetTag : {}", id);
        return questionSetTagRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QuestionSetTag : {}", id);
        questionSetTagRepository.deleteById(id);
    }
}
