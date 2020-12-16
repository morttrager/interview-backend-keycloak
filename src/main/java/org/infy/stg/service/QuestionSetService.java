package org.infy.stg.service;

import org.infy.stg.domain.QuestionSet;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionSet}.
 */
public interface QuestionSetService {

    /**
     * Save a questionSet.
     *
     * @param questionSet the entity to save.
     * @return the persisted entity.
     */
    QuestionSet save(QuestionSet questionSet);

    /**
     * Get all the questionSets.
     *
     * @return the list of entities.
     */
    List<QuestionSet> findAll();


    /**
     * Get the "id" questionSet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionSet> findOne(Long id);

    /**
     * Delete the "id" questionSet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
