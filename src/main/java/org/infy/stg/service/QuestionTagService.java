package org.infy.stg.service;

import org.infy.stg.domain.QuestionTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionTag}.
 */
public interface QuestionTagService {

    /**
     * Save a questionTag.
     *
     * @param questionTag the entity to save.
     * @return the persisted entity.
     */
    QuestionTag save(QuestionTag questionTag);

    /**
     * Get all the questionTags.
     *
     * @return the list of entities.
     */
    List<QuestionTag> findAll();


    /**
     * Get the "id" questionTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionTag> findOne(Long id);

    /**
     * Delete the "id" questionTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
