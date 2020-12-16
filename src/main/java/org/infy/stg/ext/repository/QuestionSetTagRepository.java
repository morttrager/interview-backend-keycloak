package org.infy.stg.ext.repository;

import org.infy.stg.domain.QuestionSetTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the QuestionSetTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionSetTagRepository extends org.infy.stg.repository.QuestionSetTagRepository{

    List<QuestionSetTag> findByQuestionsetId(Long questionsetId);

    List<QuestionSetTag> findByQuestionsetName(String questionSetName);

}
