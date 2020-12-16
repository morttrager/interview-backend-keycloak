package org.infy.stg.ext.service;

import org.infy.stg.domain.Question;
import org.infy.stg.domain.QuestionSet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Question}.
 */
public interface QuestionSetService extends org.infy.stg.service.QuestionSetService{

    QuestionSet getQuestionSetByName(String name);

}
