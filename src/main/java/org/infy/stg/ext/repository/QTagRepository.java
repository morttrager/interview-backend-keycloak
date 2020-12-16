package org.infy.stg.ext.repository;

import org.infy.stg.domain.QTag;

import org.infy.stg.domain.Question;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QTagRepository extends org.infy.stg.repository.QTagRepository {

    QTag findByName(String name);
}
