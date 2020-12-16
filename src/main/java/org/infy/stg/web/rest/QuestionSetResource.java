package org.infy.stg.web.rest;

import org.infy.stg.domain.QuestionSet;
import org.infy.stg.service.QuestionSetService;
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
 * REST controller for managing {@link org.infy.stg.domain.QuestionSet}.
 */
@RestController
@RequestMapping("/api")
public class QuestionSetResource {

    private final Logger log = LoggerFactory.getLogger(QuestionSetResource.class);

    private static final String ENTITY_NAME = "questionSet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionSetService questionSetService;

    public QuestionSetResource(QuestionSetService questionSetService) {
        this.questionSetService = questionSetService;
    }

    /**
     * {@code POST  /question-sets} : Create a new questionSet.
     *
     * @param questionSet the questionSet to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionSet, or with status {@code 400 (Bad Request)} if the questionSet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-sets")
    public ResponseEntity<QuestionSet> createQuestionSet(@Valid @RequestBody QuestionSet questionSet) throws URISyntaxException {
        log.debug("REST request to save QuestionSet : {}", questionSet);
        if (questionSet.getId() != null) {
            throw new BadRequestAlertException("A new questionSet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionSet result = questionSetService.save(questionSet);
        return ResponseEntity.created(new URI("/api/question-sets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-sets} : Updates an existing questionSet.
     *
     * @param questionSet the questionSet to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionSet,
     * or with status {@code 400 (Bad Request)} if the questionSet is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionSet couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-sets")
    public ResponseEntity<QuestionSet> updateQuestionSet(@Valid @RequestBody QuestionSet questionSet) throws URISyntaxException {
        log.debug("REST request to update QuestionSet : {}", questionSet);
        if (questionSet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionSet result = questionSetService.save(questionSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionSet.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-sets} : get all the questionSets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionSets in body.
     */
    @GetMapping("/question-sets")
    public List<QuestionSet> getAllQuestionSets() {
        log.debug("REST request to get all QuestionSets");
        return questionSetService.findAll();
    }

    /**
     * {@code GET  /question-sets/:id} : get the "id" questionSet.
     *
     * @param id the id of the questionSet to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionSet, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-sets/{id}")
    public ResponseEntity<QuestionSet> getQuestionSet(@PathVariable Long id) {
        log.debug("REST request to get QuestionSet : {}", id);
        Optional<QuestionSet> questionSet = questionSetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionSet);
    }

    /**
     * {@code DELETE  /question-sets/:id} : delete the "id" questionSet.
     *
     * @param id the id of the questionSet to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-sets/{id}")
    public ResponseEntity<Void> deleteQuestionSet(@PathVariable Long id) {
        log.debug("REST request to delete QuestionSet : {}", id);
        questionSetService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
