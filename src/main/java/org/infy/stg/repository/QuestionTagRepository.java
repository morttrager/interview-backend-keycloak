package org.infy.stg.repository;

import org.infy.stg.domain.QuestionTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {
}
