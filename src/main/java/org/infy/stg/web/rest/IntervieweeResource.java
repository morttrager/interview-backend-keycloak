package org.infy.stg.web.rest;

import org.infy.stg.domain.Interviewee;
import org.infy.stg.service.IntervieweeService;
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
 * REST controller for managing {@link org.infy.stg.domain.Interviewee}.
 */
@RestController
@RequestMapping("/api")
public class IntervieweeResource {

    private final Logger log = LoggerFactory.getLogger(IntervieweeResource.class);

    private static final String ENTITY_NAME = "interviewee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IntervieweeService intervieweeService;

    public IntervieweeResource(IntervieweeService intervieweeService) {
        this.intervieweeService = intervieweeService;
    }

    /**
     * {@code POST  /interviewees} : Create a new interviewee.
     *
     * @param interviewee the interviewee to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewee, or with status {@code 400 (Bad Request)} if the interviewee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/interviewees")
    public ResponseEntity<Interviewee> createInterviewee(@RequestBody Interviewee interviewee) throws URISyntaxException {
        log.debug("REST request to save Interviewee : {}", interviewee);
        if (interviewee.getId() != null) {
            throw new BadRequestAlertException("A new interviewee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Interviewee result = intervieweeService.save(interviewee);
        return ResponseEntity.created(new URI("/api/interviewees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /interviewees} : Updates an existing interviewee.
     *
     * @param interviewee the interviewee to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewee,
     * or with status {@code 400 (Bad Request)} if the interviewee is not valid,
     * or with status {@code 500 (Internal Server Error)} if the interviewee couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/interviewees")
    public ResponseEntity<Interviewee> updateInterviewee(@RequestBody Interviewee interviewee) throws URISyntaxException {
        log.debug("REST request to update Interviewee : {}", interviewee);
        if (interviewee.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Interviewee result = intervieweeService.save(interviewee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, interviewee.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /interviewees} : get all the interviewees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviewees in body.
     */
    @GetMapping("/interviewees")
    public List<Interviewee> getAllInterviewees() {
        log.debug("REST request to get all Interviewees");
        return intervieweeService.findAll();
    }

    /**
     * {@code GET  /interviewees/:id} : get the "id" interviewee.
     *
     * @param id the id of the interviewee to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewee, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/interviewees/{id}")
    public ResponseEntity<Interviewee> getInterviewee(@PathVariable Long id) {
        log.debug("REST request to get Interviewee : {}", id);
        Optional<Interviewee> interviewee = intervieweeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(interviewee);
    }

    /**
     * {@code DELETE  /interviewees/:id} : delete the "id" interviewee.
     *
     * @param id the id of the interviewee to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/interviewees/{id}")
    public ResponseEntity<Void> deleteInterviewee(@PathVariable Long id) {
        log.debug("REST request to delete Interviewee : {}", id);
        intervieweeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
