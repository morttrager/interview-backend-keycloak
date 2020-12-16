package org.infy.stg.ext.repository;

import org.infy.stg.domain.InterviewQuestion;
import org.infy.stg.domain.QuestionTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the QuestionTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionTagRepository extends org.infy.stg.repository.QuestionTagRepository, QuestionTagRepositoryCustom {

    List<QuestionTag> findByQtagName(String tagName);
    List<QuestionTag> findByQuestionId(Long questionId);
    List<QuestionTag> findByQtagIdAndQuestionWeight(Long tagId, Integer weight);
}
