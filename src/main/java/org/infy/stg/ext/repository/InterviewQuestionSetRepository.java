package org.infy.stg.ext.repository;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.infy.stg.domain.InterviewQuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the InterviewQuestionSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewQuestionSetRepository extends org.infy.stg.repository.InterviewQuestionSetRepository {

    List<InterviewQuestionSet> findByIntervieweeId(Long interviewwId);
    List<InterviewQuestionSet> findByIntervieweeIdAndActive(Long interviewwId, Boolean active);
    List<InterviewQuestionSet> findByQuestionsetName(String questionSet);
    List<InterviewQuestionSet> findByIntervieweeIdAndQuestionsetName(Long intervieweeId, String questionSetName);

}
