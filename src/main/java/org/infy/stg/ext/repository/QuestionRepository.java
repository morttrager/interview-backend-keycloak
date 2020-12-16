package org.infy.stg.ext.repository;

import org.infy.stg.domain.Question;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends org.infy.stg.repository.QuestionRepository   {
}
