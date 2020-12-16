package org.infy.stg.web.rest;

import org.infy.stg.domain.QTag;
import org.infy.stg.service.QTagService;
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
 * REST controller for managing {@link org.infy.stg.domain.QTag}.
 */
@RestController
@RequestMapping("/api")
public class QTagResource {

    private final Logger log = LoggerFactory.getLogger(QTagResource.class);

    private static final String ENTITY_NAME = "qTag";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QTagService qTagService;

    public QTagResource(QTagService qTagService) {
        this.qTagService = qTagService;
    }

    /**
     * {@code POST  /q-tags} : Create a new qTag.
     *
     * @param qTag the qTag to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new qTag, or with status {@code 400 (Bad Request)} if the qTag has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/q-tags")
    public ResponseEntity<QTag> createQTag(@RequestBody QTag qTag) throws URISyntaxException {
        log.debug("REST request to save QTag : {}", qTag);
        if (qTag.getId() != null) {
            throw new BadRequestAlertException("A new qTag cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QTag result = qTagService.save(qTag);
        return ResponseEntity.created(new URI("/api/q-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /q-tags} : Updates an existing qTag.
     *
     * @param qTag the qTag to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated qTag,
     * or with status {@code 400 (Bad Request)} if the qTag is not valid,
     * or with status {@code 500 (Internal Server Error)} if the qTag couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/q-tags")
    public ResponseEntity<QTag> updateQTag(@RequestBody QTag qTag) throws URISyntaxException {
        log.debug("REST request to update QTag : {}", qTag);
        if (qTag.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QTag result = qTagService.save(qTag);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, qTag.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /q-tags} : get all the qTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of qTags in body.
     */
    @GetMapping("/q-tags")
    public List<QTag> getAllQTags() {
        log.debug("REST request to get all QTags");
        return qTagService.findAll();
    }

    /**
     * {@code GET  /q-tags/:id} : get the "id" qTag.
     *
     * @param id the id of the qTag to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the qTag, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/q-tags/{id}")
    public ResponseEntity<QTag> getQTag(@PathVariable Long id) {
        log.debug("REST request to get QTag : {}", id);
        Optional<QTag> qTag = qTagService.findOne(id);
        return ResponseUtil.wrapOrNotFound(qTag);
    }

    /**
     * {@code DELETE  /q-tags/:id} : delete the "id" qTag.
     *
     * @param id the id of the qTag to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/q-tags/{id}")
    public ResponseEntity<Void> deleteQTag(@PathVariable Long id) {
        log.debug("REST request to delete QTag : {}", id);
        qTagService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
