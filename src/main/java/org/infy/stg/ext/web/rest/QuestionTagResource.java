package org.infy.stg.ext.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import org.infy.stg.domain.QuestionTag;
import org.infy.stg.ext.service.QuestionTagService;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * REST controller for managing {@link org.infy.stg.domain.QuestionTag}.
 */
@RestController("extQuestionTagResource")
@RequestMapping("/api/ext")
public class QuestionTagResource extends org.infy.stg.web.rest.QuestionTagResource{

    private final Logger log = LoggerFactory.getLogger(QuestionTagResource.class);

    private static final String ENTITY_NAME = "interviewDbQuestionTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionTagService questionTagService;

    public QuestionTagResource(QuestionTagService questionTagService) {
        super(questionTagService);
        this.questionTagService = questionTagService;
    }


//    @GetMapping("/interview-questions/QuestionTags/{id}")
//    public ResponseEntity getInterviewQuestion(@PathVariable Long... id) {
//        log.debug("REST request to get InterviewQuestion : {}", id);
//        Collection<QuestionTag> questionTags = questionTagService.getQuestionByTags(id);
//        System.out.println(questionTags.toString());
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
//    }


}
