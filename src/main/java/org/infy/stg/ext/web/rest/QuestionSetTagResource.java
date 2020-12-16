package org.infy.stg.ext.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.infy.stg.domain.InterviewQuestionSet;
import org.infy.stg.domain.QuestionSetTag;
import org.infy.stg.ext.service.QuestionSetTagService;
import org.infy.stg.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link QuestionSetTag}.
 */
@RestController("extInterviewQuestionSetTagResource")
@RequestMapping("/api/ext")
public class QuestionSetTagResource extends org.infy.stg.web.rest.QuestionSetTagResource{

    private final Logger log = LoggerFactory.getLogger(QuestionSetTagResource.class);

    private static final String ENTITY_NAME = "questionSetTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionSetTagService questionSetTagService;

    public QuestionSetTagResource(QuestionSetTagService questionSetTagService) {
        super(questionSetTagService);
        this.questionSetTagService = questionSetTagService;
    }

    @GetMapping("//question-set-tags/interviewees/{questionSet}")
    public List<QuestionSetTag> getCandidatesOfQuestionSet(@PathVariable String questionSet) {

        return questionSetTagService.findByQuestionsetName(questionSet);

    }

}
