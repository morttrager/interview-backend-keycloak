package org.infy.stg.service.impl;

import org.infy.stg.service.QTagService;
import org.infy.stg.domain.QTag;
import org.infy.stg.repository.QTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QTag}.
 */
@Service
@Transactional
public class QTagServiceImpl implements QTagService {

    private final Logger log = LoggerFactory.getLogger(QTagServiceImpl.class);

    private final QTagRepository qTagRepository;

    public QTagServiceImpl(QTagRepository qTagRepository) {
        this.qTagRepository = qTagRepository;
    }

    @Override
    public QTag save(QTag qTag) {
        log.debug("Request to save QTag : {}", qTag);
        return qTagRepository.save(qTag);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QTag> findAll() {
        log.debug("Request to get all QTags");
        return qTagRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<QTag> findOne(Long id) {
        log.debug("Request to get QTag : {}", id);
        return qTagRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete QTag : {}", id);
        qTagRepository.deleteById(id);
    }
}
