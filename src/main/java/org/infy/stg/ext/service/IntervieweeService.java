package org.infy.stg.ext.service;

import org.infy.stg.domain.Interviewee;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Interviewee}.
 */
public interface IntervieweeService extends org.infy.stg.service.IntervieweeService {

    public Optional<Interviewee> getIntervieweeByCandidateId(String candidateId);
}
