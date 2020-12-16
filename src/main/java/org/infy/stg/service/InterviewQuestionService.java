package org.infy.stg.service;

import org.infy.stg.domain.InterviewQuestion;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InterviewQuestion}.
 */
public interface InterviewQuestionService {

    /**
     * Save a interviewQuestion.
     *
     * @param interviewQuestion the entity to save.
     * @return the persisted entity.
     */
    InterviewQuestion save(InterviewQuestion interviewQuestion);

    /**
     * Get all the interviewQuestions.
     *
     * @return the list of entities.
     */
    List<InterviewQuestion> findAll();


    /**
     * Get the "id" interviewQuestion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterviewQuestion> findOne(Long id);

    /**
     * Delete the "id" interviewQuestion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
