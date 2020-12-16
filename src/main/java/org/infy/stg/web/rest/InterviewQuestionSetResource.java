package org.infy.stg.web.rest;

import org.infy.stg.domain.InterviewQuestionSet;
import org.infy.stg.service.InterviewQuestionSetService;
import org.infy.stg.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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
 * REST controller for managing {@link org.infy.stg.domain.InterviewQuestionSet}.
 */
@RestController
@RequestMapping("/api")
public class InterviewQuestionSetResource {

    private final Logger log = LoggerFactory.getLogger(InterviewQuestionSetResource.class);

    private static final String ENTITY_NAME = "interviewQuestionSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InterviewQuestionSetService interviewQuestionSetService;

    public InterviewQuestionSetResource(InterviewQuestionSetService interviewQuestionSetService) {
        this.interviewQuestionSetService = interviewQuestionSetService;
    }

    /**
     * {@code POST  /interview-question-sets} : Create a new interviewQuestionSet.
     *
     * @param interviewQuestionSet the interviewQuestionSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewQuestionSet, or with status {@code 400 (Bad Request)} if the interviewQuestionSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interview-question-sets")
    public ResponseEntity<InterviewQuestionSet> createInterviewQuestionSet(@RequestBody InterviewQuestionSet interviewQuestionSet) throws URISyntaxException {
        log.debug("REST request to save InterviewQuestionSet : {}", interviewQuestionSet);
        if (interviewQuestionSet.getId() != null) {
            throw new BadRequestAlertException("A new interviewQuestionSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewQuestionSet result = interviewQuestionSetService.save(interviewQuestionSet);
        return ResponseEntity.created(new URI("/api/interview-question-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interview-question-sets} : Updates an existing interviewQuestionSet.
     *
     * @param interviewQuestionSet the interviewQuestionSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewQuestionSet,
     * or with status {@code 400 (Bad Request)} if the interviewQuestionSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewQuestionSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interview-question-sets")
    public ResponseEntity<InterviewQuestionSet> updateInterviewQuestionSet(@RequestBody InterviewQuestionSet interviewQuestionSet) throws URISyntaxException {
        log.debug("REST request to update InterviewQuestionSet : {}", interviewQuestionSet);
        if (interviewQuestionSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InterviewQuestionSet result = interviewQuestionSetService.save(interviewQuestionSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interviewQuestionSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interview-question-sets} : get all the interviewQuestionSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewQuestionSets in body.
     */
    @GetMapping("/interview-question-sets")
    public List<InterviewQuestionSet> getAllInterviewQuestionSets() {
        log.debug("REST request to get all InterviewQuestionSets");
        return interviewQuestionSetService.findAll();
    }

    /**
     * {@code GET  /interview-question-sets/:id} : get the "id" interviewQuestionSet.
     *
     * @param id the id of the interviewQuestionSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewQuestionSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interview-question-sets/{id}")
    public ResponseEntity<InterviewQuestionSet> getInterviewQuestionSet(@PathVariable Long id) {
        log.debug("REST request to get InterviewQuestionSet : {}", id);
        Optional<InterviewQuestionSet> interviewQuestionSet = interviewQuestionSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewQuestionSet);
    }

    /**
     * {@code DELETE  /interview-question-sets/:id} : delete the "id" interviewQuestionSet.
     *
     * @param id the id of the interviewQuestionSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interview-question-sets/{id}")
    public ResponseEntity<Void> deleteInterviewQuestionSet(@PathVariable Long id) {
        log.debug("REST request to delete InterviewQuestionSet : {}", id);
        interviewQuestionSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
