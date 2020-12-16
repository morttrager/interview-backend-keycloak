package org.infy.stg.ext.service.impl;

import org.infy.stg.ext.service.IntervieweeService;
import org.infy.stg.domain.Interviewee;
import org.infy.stg.ext.repository.IntervieweeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Interviewee}.
 */
@Transactional
@Service("extIntervieweeService")
@Primary
public class IntervieweeServiceImpl extends org.infy.stg.service.impl.IntervieweeServiceImpl implements IntervieweeService {

    private final Logger log = LoggerFactory.getLogger(IntervieweeServiceImpl.class);

    private final IntervieweeRepository intervieweeRepository;

    public IntervieweeServiceImpl(IntervieweeRepository intervieweeRepository) {
        super(intervieweeRepository);
        this.intervieweeRepository = intervieweeRepository;
    }

    public Optional<Interviewee> getIntervieweeByCandidateId(String candidateId){
        return intervieweeRepository.getIntervieweeByCandidateId(candidateId);
    }

}
