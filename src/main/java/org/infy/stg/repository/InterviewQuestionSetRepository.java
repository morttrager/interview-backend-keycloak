package org.infy.stg.repository;

import org.infy.stg.domain.InterviewQuestionSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InterviewQuestionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewQuestionSetRepository extends JpaRepository<InterviewQuestionSet, Long> {
}
