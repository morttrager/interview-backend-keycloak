package org.infy.stg.service;

import org.infy.stg.domain.QuestionSetTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QuestionSetTag}.
 */
public interface QuestionSetTagService {

    /**
     * Save a questionSetTag.
     *
     * @param questionSetTag the entity to save.
     * @return the persisted entity.
     */
    QuestionSetTag save(QuestionSetTag questionSetTag);

    /**
     * Get all the questionSetTags.
     *
     * @return the list of entities.
     */
    List<QuestionSetTag> findAll();


    /**
     * Get the "id" questionSetTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QuestionSetTag> findOne(Long id);

    /**
     * Delete the "id" questionSetTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
