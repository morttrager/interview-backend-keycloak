package org.infy.stg.ext.web.rest;

import org.infy.stg.domain.*;
import org.infy.stg.ext.repository.InterviewQuestionSetRepository;
import org.infy.stg.ext.repository.QuestionTagRepository;
import org.infy.stg.ext.service.*;
import org.infy.stg.service.QTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController("extTestDB")
@RequestMapping("/api/ext")
public class TestDB {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionResource.class);

    @Autowired
    private QuestionTagRepository questionTagRepositoryImpl;

    @Autowired
    private QTagService qTagService;

    @Autowired
    private InterviewQuestionSetRepository interviewQuestionSetRepository;

    @Autowired
    private InterviewQuestionSetService interviewQuestionSetService;

    @Autowired
    private InterviewQuestionService interviewQuestionService;

    @Autowired
    private InterviewQuestionResource interviewQuestionResource;

    @Autowired
    private InitDB initDB;
    @GetMapping("/test/interviewflow/{pattern}/{questionSet}")
    public ResponseEntity<InterviewQuestion> executeInterview(@PathVariable String pattern, @PathVariable String questionSet) {
        log.debug("REST request to get initialize dbl̥");


        initDB.truncateAll();
        initDB.initializeFakeData();

        try {
            interviewQuestionResource.initializeInterview("vamsi",questionSet);
            for(char c :pattern.toCharArray()) {
                ResponseEntity<InterviewQuestion> interviewQuestion = interviewQuestionResource.getCurrentQuestion("vamsi");
                Long intervieweQuestionId = interviewQuestion.getBody().getId();
                Map<String, Object> json = new HashMap<>();
                json.put("score",c=='w'?8f:4f);
                json.put("response",c=='w'?'r':'w');
                interviewQuestionResource.updateScore(intervieweQuestionId, json);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

//    @GetMapping("/test/questionSet/{intervieweeId}")
//    public Optional<InterviewQuestionSet> getQuestionSetByIntervieweeId(@PathVariable @NotNull Long intervieweeId){
//
//        return interviewQuestionSetRepository.findByIntervieweeId(intervieweeId);
//    }



}
