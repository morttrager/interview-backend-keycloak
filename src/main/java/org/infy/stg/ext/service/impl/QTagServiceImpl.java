package org.infy.stg.ext.service.impl;

import org.infy.stg.ext.service.QTagService;
import org.infy.stg.domain.QTag;
import org.infy.stg.ext.repository.QTagRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link QTag}.
 */
@Service("extQTagService")
@Transactional
@Primary
public class QTagServiceImpl extends org.infy.stg.service.impl.QTagServiceImpl implements QTagService {

    private final Logger log = LoggerFactory.getLogger(QTagServiceImpl.class);

    private final QTagRepository qTagRepository;

    public QTagServiceImpl(QTagRepository qTagRepository) {
        super(qTagRepository);
        this.qTagRepository = qTagRepository;
    }

    @Override
    public QTag findByQTagName(String name){
        return qTagRepository.findByName(name);
    }
}
