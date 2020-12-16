package org.infy.stg.ext.web.rest;

import org.infy.stg.domain.*;
import org.infy.stg.ext.service.*;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.infy.stg.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link InterviewQuestionSet}.
 */
@RestController("extInterviewQuestionSetResource")
@RequestMapping("/api/ext")
public class InterviewQuestionSetResource {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionSetResource.class);

    private static final String ENTITY_NAME = "interviewQuestionSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewQuestionSetService interviewQuestionSetService;

    @Autowired
    private IntervieweeService intervieweeService;

    public InterviewQuestionSetResource(InterviewQuestionSetService interviewQuestionSetService) {
        this.interviewQuestionSetService = interviewQuestionSetService;
    }

    @GetMapping("/interview-question-sets/interviewee/questions/{questionSet}")
    public List<InterviewQuestionSet> getCandidatesOfQuestionSet(@PathVariable String questionSet) {

        return interviewQuestionSetService.findByQuestionSetName(questionSet);

    }
//
//    @GetMapping("/interview-question-sets/interviewee/questions/{candidateId}/{questionSetName}")
//    public List<InterviewQuestionSet> getInterviewQuestionSets(@PathVariable String candidateId, @PathVariable String questionSetName) {
//        log.debug("REST request to get InterviewQuestion : {}", candidateId);
//
//        Optional<Interviewee> interviewee = intervieweeService.getIntervieweeByCandidateId(candidateId);
//
//        Long intervieweeId = intervieweeService.getIntervieweeByCandidateId(candidateId).get().getId();
//        List<InterviewQuestionSet> interviewQuestionSets = interviewQuestionSetService.findByIntervieweeIdAndQuestionsetName(intervieweeId,questionSetName);
//
//        Optional<InterviewQuestionSet> opt = interviewQuestionSets.stream().max(Comparator.comparing(InterviewQuestionSet::getId));
//        InterviewQuestionSet interviewQuestionSet= null;
//        if(opt.isPresent())
//            interviewQuestionSet = opt.get();
//
//        return Collections.singletonList(interviewQuestionSet);
//
//    }

}
