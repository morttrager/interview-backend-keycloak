package org.infy.stg.ext.service.impl;

import org.infy.stg.domain.InterviewQuestionSet;
import org.infy.stg.ext.repository.InterviewQuestionSetRepository;
import org.infy.stg.ext.service.InterviewQuestionSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link InterviewQuestionSet}.
 */
@Service("extInterviewQuestionSetServiceImpl")
@Primary
@Transactional
public class InterviewQuestionSetServiceImpl extends org.infy.stg.service.impl.InterviewQuestionSetServiceImpl  implements InterviewQuestionSetService{

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionSetServiceImpl.class);

    private final InterviewQuestionSetRepository interviewQuestionSetRepository;

    public InterviewQuestionSetServiceImpl(InterviewQuestionSetRepository interviewQuestionSetRepository) {
        super(interviewQuestionSetRepository);
        this.interviewQuestionSetRepository = interviewQuestionSetRepository;
    }

    public List<InterviewQuestionSet> findByIntervieweeId(Long intervieweeId){
        return interviewQuestionSetRepository.findByIntervieweeId(intervieweeId);
    }

    public List<InterviewQuestionSet> findByIntervieweeIdAndActive(Long intervieweeId,Boolean active){
        return interviewQuestionSetRepository.findByIntervieweeIdAndActive(intervieweeId, active);
    }

    @Override
    public List<InterviewQuestionSet> findByQuestionSetName(String questionSet){
        return interviewQuestionSetRepository.findByQuestionsetName(questionSet);
    }

    @Override
    public List<InterviewQuestionSet> findByIntervieweeIdAndQuestionsetName(Long intervieweeId, String questionSetName){
        return interviewQuestionSetRepository.findByIntervieweeIdAndQuestionsetName(intervieweeId,questionSetName);
    }
}
