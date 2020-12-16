package org.infy.stg.service.impl;

import org.infy.stg.service.IntervieweeService;
import org.infy.stg.domain.Interviewee;
import org.infy.stg.repository.IntervieweeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Interviewee}.
 */
@Service
@Transactional
public class IntervieweeServiceImpl implements IntervieweeService {

    private final Logger log = LoggerFactory.getLogger(IntervieweeServiceImpl.class);

    private final IntervieweeRepository intervieweeRepository;

    public IntervieweeServiceImpl(IntervieweeRepository intervieweeRepository) {
        this.intervieweeRepository = intervieweeRepository;
    }

    @Override
    public Interviewee save(Interviewee interviewee) {
        log.debug("Request to save Interviewee : {}", interviewee);
        return intervieweeRepository.save(interviewee);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Interviewee> findAll() {
        log.debug("Request to get all Interviewees");
        return intervieweeRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Interviewee> findOne(Long id) {
        log.debug("Request to get Interviewee : {}", id);
        return intervieweeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interviewee : {}", id);
        intervieweeRepository.deleteById(id);
    }
}
