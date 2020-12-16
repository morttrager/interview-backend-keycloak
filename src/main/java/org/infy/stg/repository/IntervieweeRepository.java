package org.infy.stg.repository;

import org.infy.stg.domain.Interviewee;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Interviewee entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntervieweeRepository extends JpaRepository<Interviewee, Long> {
}
