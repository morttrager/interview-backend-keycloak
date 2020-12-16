package org.infy.stg.repository;

import org.infy.stg.domain.InterviewQuestion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the InterviewQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {
}
