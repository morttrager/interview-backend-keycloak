package org.infy.stg.ext.repository;

import org.infy.stg.domain.QTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public interface  QuestionTagRepositoryCustom{

    public List getQuestionByTags(List<QTag> qTags);

}


