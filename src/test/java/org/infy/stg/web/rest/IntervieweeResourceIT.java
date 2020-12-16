package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.Interviewee;
import org.infy.stg.repository.IntervieweeRepository;
import org.infy.stg.service.IntervieweeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IntervieweeResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IntervieweeResourceIT {

    private static final String DEFAULT_CANDIDATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CANDIDATE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_REMAINING = 1;
    private static final Integer UPDATED_REMAINING = 2;

    @Autowired
    private IntervieweeRepository intervieweeRepository;

    @Autowired
    private IntervieweeService intervieweeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntervieweeMockMvc;

    private Interviewee interviewee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interviewee createEntity(EntityManager em) {
        Interviewee interviewee = new Interviewee()
            .candidateId(DEFAULT_CANDIDATE_ID)
            .remaining(DEFAULT_REMAINING);
        return interviewee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Interviewee createUpdatedEntity(EntityManager em) {
        Interviewee interviewee = new Interviewee()
            .candidateId(UPDATED_CANDIDATE_ID)
            .remaining(UPDATED_REMAINING);
        return interviewee;
    }

    @BeforeEach
    public void initTest() {
        interviewee = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewee() throws Exception {
        int databaseSizeBeforeCreate = intervieweeRepository.findAll().size();
        // Create the Interviewee
        restIntervieweeMockMvc.perform(post("/api/interviewees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewee)))
            .andExpect(status().isCreated());

        // Validate the Interviewee in the database
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();
        assertThat(intervieweeList).hasSize(databaseSizeBeforeCreate + 1);
        Interviewee testInterviewee = intervieweeList.get(intervieweeList.size() - 1);
        assertThat(testInterviewee.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testInterviewee.getRemaining()).isEqualTo(DEFAULT_REMAINING);
    }

    @Test
    @Transactional
    public void createIntervieweeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intervieweeRepository.findAll().size();

        // Create the Interviewee with an existing ID
        interviewee.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntervieweeMockMvc.perform(post("/api/interviewees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewee)))
            .andExpect(status().isBadRequest());

        // Validate the Interviewee in the database
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();
        assertThat(intervieweeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterviewees() throws Exception {
        // Initialize the database
        intervieweeRepository.saveAndFlush(interviewee);

        // Get all the intervieweeList
        restIntervieweeMockMvc.perform(get("/api/interviewees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewee.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID)))
            .andExpect(jsonPath("$.[*].remaining").value(hasItem(DEFAULT_REMAINING)));
    }
    
    @Test
    @Transactional
    public void getInterviewee() throws Exception {
        // Initialize the database
        intervieweeRepository.saveAndFlush(interviewee);

        // Get the interviewee
        restIntervieweeMockMvc.perform(get("/api/interviewees/{id}", interviewee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewee.getId().intValue()))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID))
            .andExpect(jsonPath("$.remaining").value(DEFAULT_REMAINING));
    }
    @Test
    @Transactional
    public void getNonExistingInterviewee() throws Exception {
        // Get the interviewee
        restIntervieweeMockMvc.perform(get("/api/interviewees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewee() throws Exception {
        // Initialize the database
        intervieweeService.save(interviewee);

        int databaseSizeBeforeUpdate = intervieweeRepository.findAll().size();

        // Update the interviewee
        Interviewee updatedInterviewee = intervieweeRepository.findById(interviewee.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewee are not directly saved in db
        em.detach(updatedInterviewee);
        updatedInterviewee
            .candidateId(UPDATED_CANDIDATE_ID)
            .remaining(UPDATED_REMAINING);

        restIntervieweeMockMvc.perform(put("/api/interviewees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterviewee)))
            .andExpect(status().isOk());

        // Validate the Interviewee in the database
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();
        assertThat(intervieweeList).hasSize(databaseSizeBeforeUpdate);
        Interviewee testInterviewee = intervieweeList.get(intervieweeList.size() - 1);
        assertThat(testInterviewee.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testInterviewee.getRemaining()).isEqualTo(UPDATED_REMAINING);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewee() throws Exception {
        int databaseSizeBeforeUpdate = intervieweeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntervieweeMockMvc.perform(put("/api/interviewees").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewee)))
            .andExpect(status().isBadRequest());

        // Validate the Interviewee in the database
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();
        assertThat(intervieweeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterviewee() throws Exception {
        // Initialize the database
        intervieweeService.save(interviewee);

        int databaseSizeBeforeDelete = intervieweeRepository.findAll().size();

        // Delete the interviewee
        restIntervieweeMockMvc.perform(delete("/api/interviewees/{id}", interviewee.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Interviewee> intervieweeList = intervieweeRepository.findAll();
        assertThat(intervieweeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
