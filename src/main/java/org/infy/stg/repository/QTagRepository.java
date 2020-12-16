package org.infy.stg.repository;

import org.infy.stg.domain.QTag;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QTag entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QTagRepository extends JpaRepository<QTag, Long> {
}
