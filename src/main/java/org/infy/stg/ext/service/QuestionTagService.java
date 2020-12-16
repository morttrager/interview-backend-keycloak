package org.infy.stg.ext.service;

import org.infy.stg.domain.Question;
import org.infy.stg.domain.QuestionTag;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionTag}.
 */
public interface QuestionTagService extends org.infy.stg.service.QuestionTagService {

    List<QuestionTag> findQuestionsForTopic(String tagName);
    List<QuestionTag> getQTags(Long questionId);
    List<QuestionTag> findByQtagIdAndQuestionWeight(Long tagId, Integer weight);

}
