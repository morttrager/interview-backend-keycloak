package org.infy.stg.ext.repository;

import org.infy.stg.domain.InterviewQuestion;

import org.infy.stg.domain.Interviewee;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the InterviewQuestion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewQuestionRepository extends org.infy.stg.repository.InterviewQuestionRepository {
    Optional<InterviewQuestion> findByIntervieweeIdAndScoreIsNull(Long intervieweeId);

//    @Modifying
//    @Query("update InterviewQuestion u set u.score = ?2 where u.id = ?1")
//    void updateInterviewQuestionScore (Long interviewQuestionId, Float score);

    List<InterviewQuestion> findByIntervieweeIdAndInterviewqsId(Long intervieweeId, Long interviewQuestionSetId);
    List<InterviewQuestion> findByInterviewqsId(Long interviewQuestionSetId);
}
