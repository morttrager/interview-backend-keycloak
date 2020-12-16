package org.infy.stg.ext.repository;

import org.infy.stg.domain.Interviewee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data  repository for the Interviewee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervieweeRepository extends org.infy.stg.repository.IntervieweeRepository {

    Optional<Interviewee> getIntervieweeByCandidateId(String candidateId);
}
