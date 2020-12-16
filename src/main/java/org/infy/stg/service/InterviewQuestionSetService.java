package org.infy.stg.service;

import org.infy.stg.domain.InterviewQuestionSet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link InterviewQuestionSet}.
 */
public interface InterviewQuestionSetService {

    /**
     * Save a interviewQuestionSet.
     *
     * @param interviewQuestionSet the entity to save.
     * @return the persisted entity.
     */
    InterviewQuestionSet save(InterviewQuestionSet interviewQuestionSet);

    /**
     * Get all the interviewQuestionSets.
     *
     * @return the list of entities.
     */
    List<InterviewQuestionSet> findAll();


    /**
     * Get the "id" interviewQuestionSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InterviewQuestionSet> findOne(Long id);

    /**
     * Delete the "id" interviewQuestionSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
