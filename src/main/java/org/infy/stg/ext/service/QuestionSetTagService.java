package org.infy.stg.ext.service;

import org.infy.stg.domain.QuestionSetTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionSetTag}.
 */
public interface QuestionSetTagService extends  org.infy.stg.service.QuestionSetTagService{

    List<QuestionSetTag> findByQuestionsetId(Long questionsetId);
    List<QuestionSetTag> findByQuestionsetName(String questionSetName);

}
