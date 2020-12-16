package org.infy.stg.web.rest;

import org.infy.stg.domain.QuestionTag;
import org.infy.stg.service.QuestionTagService;
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
 * REST controller for managing {@link org.infy.stg.domain.QuestionTag}.
 */
@RestController
@RequestMapping("/api")
public class QuestionTagResource {

    private final Logger log = LoggerFactory.getLogger(QuestionTagResource.class);

    private static final String ENTITY_NAME = "questionTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionTagService questionTagService;

    public QuestionTagResource(QuestionTagService questionTagService) {
        this.questionTagService = questionTagService;
    }

    /**
     * {@code POST  /question-tags} : Create a new questionTag.
     *
     * @param questionTag the questionTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionTag, or with status {@code 400 (Bad Request)} if the questionTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-tags")
    public ResponseEntity<QuestionTag> createQuestionTag(@RequestBody QuestionTag questionTag) throws URISyntaxException {
        log.debug("REST request to save QuestionTag : {}", questionTag);
        if (questionTag.getId() != null) {
            throw new BadRequestAlertException("A new questionTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionTag result = questionTagService.save(questionTag);
        return ResponseEntity.created(new URI("/api/question-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-tags} : Updates an existing questionTag.
     *
     * @param questionTag the questionTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionTag,
     * or with status {@code 400 (Bad Request)} if the questionTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-tags")
    public ResponseEntity<QuestionTag> updateQuestionTag(@RequestBody QuestionTag questionTag) throws URISyntaxException {
        log.debug("REST request to update QuestionTag : {}", questionTag);
        if (questionTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionTag result = questionTagService.save(questionTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-tags} : get all the questionTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionTags in body.
     */
    @GetMapping("/question-tags")
    public List<QuestionTag> getAllQuestionTags() {
        log.debug("REST request to get all QuestionTags");
        return questionTagService.findAll();
    }

    /**
     * {@code GET  /question-tags/:id} : get the "id" questionTag.
     *
     * @param id the id of the questionTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-tags/{id}")
    public ResponseEntity<QuestionTag> getQuestionTag(@PathVariable Long id) {
        log.debug("REST request to get QuestionTag : {}", id);
        Optional<QuestionTag> questionTag = questionTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionTag);
    }

    /**
     * {@code DELETE  /question-tags/:id} : delete the "id" questionTag.
     *
     * @param id the id of the questionTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-tags/{id}")
    public ResponseEntity<Void> deleteQuestionTag(@PathVariable Long id) {
        log.debug("REST request to delete QuestionTag : {}", id);
        questionTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
