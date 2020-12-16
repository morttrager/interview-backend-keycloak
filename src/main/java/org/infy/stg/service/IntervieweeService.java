package org.infy.stg.service;

import org.infy.stg.domain.Interviewee;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Interviewee}.
 */
public interface IntervieweeService {

    /**
     * Save a interviewee.
     *
     * @param interviewee the entity to save.
     * @return the persisted entity.
     */
    Interviewee save(Interviewee interviewee);

    /**
     * Get all the interviewees.
     *
     * @return the list of entities.
     */
    List<Interviewee> findAll();


    /**
     * Get the "id" interviewee.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Interviewee> findOne(Long id);

    /**
     * Delete the "id" interviewee.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
