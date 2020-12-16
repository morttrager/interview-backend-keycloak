package org.infy.stg.ext.repository;

import org.infy.stg.domain.QuestionSet;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionSetRepository extends org.infy.stg.repository.QuestionSetRepository   {
    QuestionSet findByName(String name);
}
