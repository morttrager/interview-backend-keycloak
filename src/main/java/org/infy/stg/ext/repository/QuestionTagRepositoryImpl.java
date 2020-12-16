package org.infy.stg.ext.repository;

import org.infy.stg.domain.QTag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class   QuestionTagRepositoryImpl implements QuestionTagRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    public List getQuestionByTags(List<QTag> qTags){
        System.out.println("Entity Manager : "+entityManager);

        String tableJoin = "FROM ";
        String whereClause = "WHERE ";
        String questionClause = "";
        String tagClause = "";
        for ( int index=0; index<qTags.size(); index++) {



            if (index+1 == qTags.size()){
                tableJoin += "question_tag qt"+(index+1);
                tagClause += "qt"+(index+1)+".qtag_id="+qTags.get(index).getId()+" ";
            }
            else {
                tableJoin += "question_tag qt"+(index+1)+" ,";
                tagClause += "qt"+(index+1)+".qtag_id="+qTags.get(index).getId()+" AND ";
            }

            if (index+2==qTags.size()) {
                whereClause += "qt"+(index+1)+".question_id="+"qt"+(index+2)+".question_id ";
            }
            else if(index+2 <qTags.size()) {
                whereClause += "qt"+(index+1)+".question_id="+"qt"+(index+2)+".question_id AND ";
            }



        }
        System.out.println(tableJoin+" "+whereClause+" AND "+tagClause);
        Query nativeQuery = entityManager.createNativeQuery("SELECT qt1.question_id " + tableJoin+" "+whereClause+" AND "+tagClause);

        List resultList = nativeQuery.getResultList();

        return resultList;
    }
}


