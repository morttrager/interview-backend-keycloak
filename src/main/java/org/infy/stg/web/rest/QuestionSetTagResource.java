package org.infy.stg.web.rest;

import org.infy.stg.domain.QuestionSetTag;
import org.infy.stg.service.QuestionSetTagService;
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
 * REST controller for managing {@link org.infy.stg.domain.QuestionSetTag}.
 */
@RestController
@RequestMapping("/api")
public class QuestionSetTagResource {

    private final Logger log = LoggerFactory.getLogger(QuestionSetTagResource.class);

    private static final String ENTITY_NAME = "questionSetTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionSetTagService questionSetTagService;

    public QuestionSetTagResource(QuestionSetTagService questionSetTagService) {
        this.questionSetTagService = questionSetTagService;
    }

    /**
     * {@code POST  /question-set-tags} : Create a new questionSetTag.
     *
     * @param questionSetTag the questionSetTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionSetTag, or with status {@code 400 (Bad Request)} if the questionSetTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-set-tags")
    public ResponseEntity<QuestionSetTag> createQuestionSetTag(@RequestBody QuestionSetTag questionSetTag) throws URISyntaxException {
        log.debug("REST request to save QuestionSetTag : {}", questionSetTag);
        if (questionSetTag.getId() != null) {
            throw new BadRequestAlertException("A new questionSetTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionSetTag result = questionSetTagService.save(questionSetTag);
        return ResponseEntity.created(new URI("/api/question-set-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-set-tags} : Updates an existing questionSetTag.
     *
     * @param questionSetTag the questionSetTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionSetTag,
     * or with status {@code 400 (Bad Request)} if the questionSetTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionSetTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-set-tags")
    public ResponseEntity<QuestionSetTag> updateQuestionSetTag(@RequestBody QuestionSetTag questionSetTag) throws URISyntaxException {
        log.debug("REST request to update QuestionSetTag : {}", questionSetTag);
        if (questionSetTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionSetTag result = questionSetTagService.save(questionSetTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, questionSetTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-set-tags} : get all the questionSetTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionSetTags in body.
     */
    @GetMapping("/question-set-tags")
    public List<QuestionSetTag> getAllQuestionSetTags() {
        log.debug("REST request to get all QuestionSetTags");
        return questionSetTagService.findAll();
    }

    /**
     * {@code GET  /question-set-tags/:id} : get the "id" questionSetTag.
     *
     * @param id the id of the questionSetTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionSetTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-set-tags/{id}")
    public ResponseEntity<QuestionSetTag> getQuestionSetTag(@PathVariable Long id) {
        log.debug("REST request to get QuestionSetTag : {}", id);
        Optional<QuestionSetTag> questionSetTag = questionSetTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionSetTag);
    }

    /**
     * {@code DELETE  /question-set-tags/:id} : delete the "id" questionSetTag.
     *
     * @param id the id of the questionSetTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-set-tags/{id}")
    public ResponseEntity<Void> deleteQuestionSetTag(@PathVariable Long id) {
        log.debug("REST request to delete QuestionSetTag : {}", id);
        questionSetTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
