package org.infy.stg.ext.service;

import org.infy.stg.domain.QTag;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link QTag}.
 */
public interface QTagService extends org.infy.stg.service.QTagService{

    QTag findByQTagName(String name);

}
