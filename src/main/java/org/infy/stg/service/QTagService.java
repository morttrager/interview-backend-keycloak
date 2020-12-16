package org.infy.stg.service;

import org.infy.stg.domain.QTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QTag}.
 */
public interface QTagService {

    /**
     * Save a qTag.
     *
     * @param qTag the entity to save.
     * @return the persisted entity.
     */
    QTag save(QTag qTag);

    /**
     * Get all the qTags.
     *
     * @return the list of entities.
     */
    List<QTag> findAll();


    /**
     * Get the "id" qTag.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<QTag> findOne(Long id);

    /**
     * Delete the "id" qTag.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
