package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.InterviewQuestionSet;
import org.infy.stg.repository.InterviewQuestionSetRepository;
import org.infy.stg.service.InterviewQuestionSetService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InterviewQuestionSetResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InterviewQuestionSetResourceIT {

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_ABANDONED = false;
    private static final Boolean UPDATED_ABANDONED = true;

    private static final Float DEFAULT_NET_SCORE = 1F;
    private static final Float UPDATED_NET_SCORE = 2F;

    private static final Integer DEFAULT_NET_QUESTIONS = 1;
    private static final Integer UPDATED_NET_QUESTIONS = 2;

    private static final Integer DEFAULT_NET_WEIGHT = 1;
    private static final Integer UPDATED_NET_WEIGHT = 2;

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InterviewQuestionSetRepository interviewQuestionSetRepository;

    @Autowired
    private InterviewQuestionSetService interviewQuestionSetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewQuestionSetMockMvc;

    private InterviewQuestionSet interviewQuestionSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewQuestionSet createEntity(EntityManager em) {
        InterviewQuestionSet interviewQuestionSet = new InterviewQuestionSet()
            .active(DEFAULT_ACTIVE)
            .abandoned(DEFAULT_ABANDONED)
            .netScore(DEFAULT_NET_SCORE)
            .netQuestions(DEFAULT_NET_QUESTIONS)
            .netWeight(DEFAULT_NET_WEIGHT)
            .time(DEFAULT_TIME);
        return interviewQuestionSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewQuestionSet createUpdatedEntity(EntityManager em) {
        InterviewQuestionSet interviewQuestionSet = new InterviewQuestionSet()
            .active(UPDATED_ACTIVE)
            .abandoned(UPDATED_ABANDONED)
            .netScore(UPDATED_NET_SCORE)
            .netQuestions(UPDATED_NET_QUESTIONS)
            .netWeight(UPDATED_NET_WEIGHT)
            .time(UPDATED_TIME);
        return interviewQuestionSet;
    }

    @BeforeEach
    public void initTest() {
        interviewQuestionSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewQuestionSet() throws Exception {
        int databaseSizeBeforeCreate = interviewQuestionSetRepository.findAll().size();
        // Create the InterviewQuestionSet
        restInterviewQuestionSetMockMvc.perform(post("/api/interview-question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestionSet)))
            .andExpect(status().isCreated());

        // Validate the InterviewQuestionSet in the database
        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetRepository.findAll();
        assertThat(interviewQuestionSetList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewQuestionSet testInterviewQuestionSet = interviewQuestionSetList.get(interviewQuestionSetList.size() - 1);
        assertThat(testInterviewQuestionSet.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testInterviewQuestionSet.isAbandoned()).isEqualTo(DEFAULT_ABANDONED);
        assertThat(testInterviewQuestionSet.getNetScore()).isEqualTo(DEFAULT_NET_SCORE);
        assertThat(testInterviewQuestionSet.getNetQuestions()).isEqualTo(DEFAULT_NET_QUESTIONS);
        assertThat(testInterviewQuestionSet.getNetWeight()).isEqualTo(DEFAULT_NET_WEIGHT);
        assertThat(testInterviewQuestionSet.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createInterviewQuestionSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewQuestionSetRepository.findAll().size();

        // Create the InterviewQuestionSet with an existing ID
        interviewQuestionSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewQuestionSetMockMvc.perform(post("/api/interview-question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestionSet)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewQuestionSet in the database
        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetRepository.findAll();
        assertThat(interviewQuestionSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterviewQuestionSets() throws Exception {
        // Initialize the database
        interviewQuestionSetRepository.saveAndFlush(interviewQuestionSet);

        // Get all the interviewQuestionSetList
        restInterviewQuestionSetMockMvc.perform(get("/api/interview-question-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewQuestionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].abandoned").value(hasItem(DEFAULT_ABANDONED.booleanValue())))
            .andExpect(jsonPath("$.[*].netScore").value(hasItem(DEFAULT_NET_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].netQuestions").value(hasItem(DEFAULT_NET_QUESTIONS)))
            .andExpect(jsonPath("$.[*].netWeight").value(hasItem(DEFAULT_NET_WEIGHT)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getInterviewQuestionSet() throws Exception {
        // Initialize the database
        interviewQuestionSetRepository.saveAndFlush(interviewQuestionSet);

        // Get the interviewQuestionSet
        restInterviewQuestionSetMockMvc.perform(get("/api/interview-question-sets/{id}", interviewQuestionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewQuestionSet.getId().intValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.abandoned").value(DEFAULT_ABANDONED.booleanValue()))
            .andExpect(jsonPath("$.netScore").value(DEFAULT_NET_SCORE.doubleValue()))
            .andExpect(jsonPath("$.netQuestions").value(DEFAULT_NET_QUESTIONS))
            .andExpect(jsonPath("$.netWeight").value(DEFAULT_NET_WEIGHT))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInterviewQuestionSet() throws Exception {
        // Get the interviewQuestionSet
        restInterviewQuestionSetMockMvc.perform(get("/api/interview-question-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewQuestionSet() throws Exception {
        // Initialize the database
        interviewQuestionSetService.save(interviewQuestionSet);

        int databaseSizeBeforeUpdate = interviewQuestionSetRepository.findAll().size();

        // Update the interviewQuestionSet
        InterviewQuestionSet updatedInterviewQuestionSet = interviewQuestionSetRepository.findById(interviewQuestionSet.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewQuestionSet are not directly saved in db
        em.detach(updatedInterviewQuestionSet);
        updatedInterviewQuestionSet
            .active(UPDATED_ACTIVE)
            .abandoned(UPDATED_ABANDONED)
            .netScore(UPDATED_NET_SCORE)
            .netQuestions(UPDATED_NET_QUESTIONS)
            .netWeight(UPDATED_NET_WEIGHT)
            .time(UPDATED_TIME);

        restInterviewQuestionSetMockMvc.perform(put("/api/interview-question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterviewQuestionSet)))
            .andExpect(status().isOk());

        // Validate the InterviewQuestionSet in the database
        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetRepository.findAll();
        assertThat(interviewQuestionSetList).hasSize(databaseSizeBeforeUpdate);
        InterviewQuestionSet testInterviewQuestionSet = interviewQuestionSetList.get(interviewQuestionSetList.size() - 1);
        assertThat(testInterviewQuestionSet.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testInterviewQuestionSet.isAbandoned()).isEqualTo(UPDATED_ABANDONED);
        assertThat(testInterviewQuestionSet.getNetScore()).isEqualTo(UPDATED_NET_SCORE);
        assertThat(testInterviewQuestionSet.getNetQuestions()).isEqualTo(UPDATED_NET_QUESTIONS);
        assertThat(testInterviewQuestionSet.getNetWeight()).isEqualTo(UPDATED_NET_WEIGHT);
        assertThat(testInterviewQuestionSet.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewQuestionSet() throws Exception {
        int databaseSizeBeforeUpdate = interviewQuestionSetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewQuestionSetMockMvc.perform(put("/api/interview-question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestionSet)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewQuestionSet in the database
        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetRepository.findAll();
        assertThat(interviewQuestionSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterviewQuestionSet() throws Exception {
        // Initialize the database
        interviewQuestionSetService.save(interviewQuestionSet);

        int databaseSizeBeforeDelete = interviewQuestionSetRepository.findAll().size();

        // Delete the interviewQuestionSet
        restInterviewQuestionSetMockMvc.perform(delete("/api/interview-question-sets/{id}", interviewQuestionSet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterviewQuestionSet> interviewQuestionSetList = interviewQuestionSetRepository.findAll();
        assertThat(interviewQuestionSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
