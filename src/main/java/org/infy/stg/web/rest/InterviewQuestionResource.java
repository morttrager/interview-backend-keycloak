package org.infy.stg.web.rest;

import org.infy.stg.domain.InterviewQuestion;
import org.infy.stg.service.InterviewQuestionService;
import org.infy.stg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.infy.stg.domain.InterviewQuestion}.
 */
@RestController
@RequestMapping("/api")
public class InterviewQuestionResource {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionResource.class);

    private static final String ENTITY_NAME = "interviewQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewQuestionService interviewQuestionService;

    public InterviewQuestionResource(InterviewQuestionService interviewQuestionService) {
        this.interviewQuestionService = interviewQuestionService;
    }

    /**
     * {@code POST  /interview-questions} : Create a new interviewQuestion.
     *
     * @param interviewQuestion the interviewQuestion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewQuestion, or with status {@code 400 (Bad Request)} if the interviewQuestion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-questions")
    public ResponseEntity<InterviewQuestion> createInterviewQuestion(@Valid @RequestBody InterviewQuestion interviewQuestion) throws URISyntaxException {
        log.debug("REST request to save InterviewQuestion : {}", interviewQuestion);
        if (interviewQuestion.getId() != null) {
            throw new BadRequestAlertException("A new interviewQuestion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewQuestion result = interviewQuestionService.save(interviewQuestion);
        return ResponseEntity.created(new URI("/api/interview-questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-questions} : Updates an existing interviewQuestion.
     *
     * @param interviewQuestion the interviewQuestion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewQuestion,
     * or with status {@code 400 (Bad Request)} if the interviewQuestion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewQuestion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-questions")
    public ResponseEntity<InterviewQuestion> updateInterviewQuestion(@Valid @RequestBody InterviewQuestion interviewQuestion) throws URISyntaxException {
        log.debug("REST request to update InterviewQuestion : {}", interviewQuestion);
        if (interviewQuestion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewQuestion result = interviewQuestionService.save(interviewQuestion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interviewQuestion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interview-questions} : get all the interviewQuestions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewQuestions in body.
     */
    @GetMapping("/interview-questions")
    public List<InterviewQuestion> getAllInterviewQuestions() {
        log.debug("REST request to get all InterviewQuestions");
        return interviewQuestionService.findAll();
    }

    /**
     * {@code GET  /interview-questions/:id} : get the "id" interviewQuestion.
     *
     * @param id the id of the interviewQuestion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewQuestion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-questions/{id}")
    public ResponseEntity<InterviewQuestion> getInterviewQuestion(@PathVariable Long id) {
        log.debug("REST request to get InterviewQuestion : {}", id);
        Optional<InterviewQuestion> interviewQuestion = interviewQuestionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewQuestion);
    }

    /**
     * {@code DELETE  /interview-questions/:id} : delete the "id" interviewQuestion.
     *
     * @param id the id of the interviewQuestion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-questions/{id}")
    public ResponseEntity<Void> deleteInterviewQuestion(@PathVariable Long id) {
        log.debug("REST request to delete InterviewQuestion : {}", id);
        interviewQuestionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
