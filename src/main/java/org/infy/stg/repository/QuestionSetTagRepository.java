package org.infy.stg.repository;

import org.infy.stg.domain.QuestionSetTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QuestionSetTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionSetTagRepository extends JpaRepository<QuestionSetTag, Long> {
}
