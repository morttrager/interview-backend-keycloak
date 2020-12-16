package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.InterviewQuestion;
import org.infy.stg.repository.InterviewQuestionRepository;
import org.infy.stg.service.InterviewQuestionService;

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
 * Integration tests for the {@link InterviewQuestionResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class InterviewQuestionResourceIT {

    private static final Float DEFAULT_SCORE = 1F;
    private static final Float UPDATED_SCORE = 2F;

    private static final String DEFAULT_RESPONE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONE = "BBBBBBBBBB";

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InterviewQuestionRepository interviewQuestionRepository;

    @Autowired
    private InterviewQuestionService interviewQuestionService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInterviewQuestionMockMvc;

    private InterviewQuestion interviewQuestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewQuestion createEntity(EntityManager em) {
        InterviewQuestion interviewQuestion = new InterviewQuestion()
            .score(DEFAULT_SCORE)
            .respone(DEFAULT_RESPONE)
            .time(DEFAULT_TIME);
        return interviewQuestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InterviewQuestion createUpdatedEntity(EntityManager em) {
        InterviewQuestion interviewQuestion = new InterviewQuestion()
            .score(UPDATED_SCORE)
            .respone(UPDATED_RESPONE)
            .time(UPDATED_TIME);
        return interviewQuestion;
    }

    @BeforeEach
    public void initTest() {
        interviewQuestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewQuestion() throws Exception {
        int databaseSizeBeforeCreate = interviewQuestionRepository.findAll().size();
        // Create the InterviewQuestion
        restInterviewQuestionMockMvc.perform(post("/api/interview-questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestion)))
            .andExpect(status().isCreated());

        // Validate the InterviewQuestion in the database
        List<InterviewQuestion> interviewQuestionList = interviewQuestionRepository.findAll();
        assertThat(interviewQuestionList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewQuestion testInterviewQuestion = interviewQuestionList.get(interviewQuestionList.size() - 1);
        assertThat(testInterviewQuestion.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testInterviewQuestion.getRespone()).isEqualTo(DEFAULT_RESPONE);
        assertThat(testInterviewQuestion.getTime()).isEqualTo(DEFAULT_TIME);
    }

    @Test
    @Transactional
    public void createInterviewQuestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewQuestionRepository.findAll().size();

        // Create the InterviewQuestion with an existing ID
        interviewQuestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewQuestionMockMvc.perform(post("/api/interview-questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewQuestion in the database
        List<InterviewQuestion> interviewQuestionList = interviewQuestionRepository.findAll();
        assertThat(interviewQuestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInterviewQuestions() throws Exception {
        // Initialize the database
        interviewQuestionRepository.saveAndFlush(interviewQuestion);

        // Get all the interviewQuestionList
        restInterviewQuestionMockMvc.perform(get("/api/interview-questions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewQuestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE.doubleValue())))
            .andExpect(jsonPath("$.[*].respone").value(hasItem(DEFAULT_RESPONE)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())));
    }
    
    @Test
    @Transactional
    public void getInterviewQuestion() throws Exception {
        // Initialize the database
        interviewQuestionRepository.saveAndFlush(interviewQuestion);

        // Get the interviewQuestion
        restInterviewQuestionMockMvc.perform(get("/api/interview-questions/{id}", interviewQuestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(interviewQuestion.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE.doubleValue()))
            .andExpect(jsonPath("$.respone").value(DEFAULT_RESPONE))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingInterviewQuestion() throws Exception {
        // Get the interviewQuestion
        restInterviewQuestionMockMvc.perform(get("/api/interview-questions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewQuestion() throws Exception {
        // Initialize the database
        interviewQuestionService.save(interviewQuestion);

        int databaseSizeBeforeUpdate = interviewQuestionRepository.findAll().size();

        // Update the interviewQuestion
        InterviewQuestion updatedInterviewQuestion = interviewQuestionRepository.findById(interviewQuestion.getId()).get();
        // Disconnect from session so that the updates on updatedInterviewQuestion are not directly saved in db
        em.detach(updatedInterviewQuestion);
        updatedInterviewQuestion
            .score(UPDATED_SCORE)
            .respone(UPDATED_RESPONE)
            .time(UPDATED_TIME);

        restInterviewQuestionMockMvc.perform(put("/api/interview-questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInterviewQuestion)))
            .andExpect(status().isOk());

        // Validate the InterviewQuestion in the database
        List<InterviewQuestion> interviewQuestionList = interviewQuestionRepository.findAll();
        assertThat(interviewQuestionList).hasSize(databaseSizeBeforeUpdate);
        InterviewQuestion testInterviewQuestion = interviewQuestionList.get(interviewQuestionList.size() - 1);
        assertThat(testInterviewQuestion.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testInterviewQuestion.getRespone()).isEqualTo(UPDATED_RESPONE);
        assertThat(testInterviewQuestion.getTime()).isEqualTo(UPDATED_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewQuestion() throws Exception {
        int databaseSizeBeforeUpdate = interviewQuestionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInterviewQuestionMockMvc.perform(put("/api/interview-questions").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(interviewQuestion)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewQuestion in the database
        List<InterviewQuestion> interviewQuestionList = interviewQuestionRepository.findAll();
        assertThat(interviewQuestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInterviewQuestion() throws Exception {
        // Initialize the database
        interviewQuestionService.save(interviewQuestion);

        int databaseSizeBeforeDelete = interviewQuestionRepository.findAll().size();

        // Delete the interviewQuestion
        restInterviewQuestionMockMvc.perform(delete("/api/interview-questions/{id}", interviewQuestion.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InterviewQuestion> interviewQuestionList = interviewQuestionRepository.findAll();
        assertThat(interviewQuestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
