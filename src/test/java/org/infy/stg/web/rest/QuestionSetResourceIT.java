package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.QuestionSet;
import org.infy.stg.repository.QuestionSetRepository;
import org.infy.stg.service.QuestionSetService;

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
 * Integration tests for the {@link QuestionSetResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuestionSetResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTRO = "AAAAAAAAAA";
    private static final String UPDATED_INTRO = "BBBBBBBBBB";

    private static final String DEFAULT_OUTRO = "AAAAAAAAAA";
    private static final String UPDATED_OUTRO = "BBBBBBBBBB";

    @Autowired
    private QuestionSetRepository questionSetRepository;

    @Autowired
    private QuestionSetService questionSetService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionSetMockMvc;

    private QuestionSet questionSet;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionSet createEntity(EntityManager em) {
        QuestionSet questionSet = new QuestionSet()
            .name(DEFAULT_NAME)
            .intro(DEFAULT_INTRO)
            .outro(DEFAULT_OUTRO);
        return questionSet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionSet createUpdatedEntity(EntityManager em) {
        QuestionSet questionSet = new QuestionSet()
            .name(UPDATED_NAME)
            .intro(UPDATED_INTRO)
            .outro(UPDATED_OUTRO);
        return questionSet;
    }

    @BeforeEach
    public void initTest() {
        questionSet = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionSet() throws Exception {
        int databaseSizeBeforeCreate = questionSetRepository.findAll().size();
        // Create the QuestionSet
        restQuestionSetMockMvc.perform(post("/api/question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSet)))
            .andExpect(status().isCreated());

        // Validate the QuestionSet in the database
        List<QuestionSet> questionSetList = questionSetRepository.findAll();
        assertThat(questionSetList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionSet testQuestionSet = questionSetList.get(questionSetList.size() - 1);
        assertThat(testQuestionSet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testQuestionSet.getIntro()).isEqualTo(DEFAULT_INTRO);
        assertThat(testQuestionSet.getOutro()).isEqualTo(DEFAULT_OUTRO);
    }

    @Test
    @Transactional
    public void createQuestionSetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionSetRepository.findAll().size();

        // Create the QuestionSet with an existing ID
        questionSet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionSetMockMvc.perform(post("/api/question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSet)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionSet in the database
        List<QuestionSet> questionSetList = questionSetRepository.findAll();
        assertThat(questionSetList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionSets() throws Exception {
        // Initialize the database
        questionSetRepository.saveAndFlush(questionSet);

        // Get all the questionSetList
        restQuestionSetMockMvc.perform(get("/api/question-sets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionSet.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].intro").value(hasItem(DEFAULT_INTRO)))
            .andExpect(jsonPath("$.[*].outro").value(hasItem(DEFAULT_OUTRO)));
    }
    
    @Test
    @Transactional
    public void getQuestionSet() throws Exception {
        // Initialize the database
        questionSetRepository.saveAndFlush(questionSet);

        // Get the questionSet
        restQuestionSetMockMvc.perform(get("/api/question-sets/{id}", questionSet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionSet.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.intro").value(DEFAULT_INTRO))
            .andExpect(jsonPath("$.outro").value(DEFAULT_OUTRO));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionSet() throws Exception {
        // Get the questionSet
        restQuestionSetMockMvc.perform(get("/api/question-sets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionSet() throws Exception {
        // Initialize the database
        questionSetService.save(questionSet);

        int databaseSizeBeforeUpdate = questionSetRepository.findAll().size();

        // Update the questionSet
        QuestionSet updatedQuestionSet = questionSetRepository.findById(questionSet.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionSet are not directly saved in db
        em.detach(updatedQuestionSet);
        updatedQuestionSet
            .name(UPDATED_NAME)
            .intro(UPDATED_INTRO)
            .outro(UPDATED_OUTRO);

        restQuestionSetMockMvc.perform(put("/api/question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionSet)))
            .andExpect(status().isOk());

        // Validate the QuestionSet in the database
        List<QuestionSet> questionSetList = questionSetRepository.findAll();
        assertThat(questionSetList).hasSize(databaseSizeBeforeUpdate);
        QuestionSet testQuestionSet = questionSetList.get(questionSetList.size() - 1);
        assertThat(testQuestionSet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testQuestionSet.getIntro()).isEqualTo(UPDATED_INTRO);
        assertThat(testQuestionSet.getOutro()).isEqualTo(UPDATED_OUTRO);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionSet() throws Exception {
        int databaseSizeBeforeUpdate = questionSetRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionSetMockMvc.perform(put("/api/question-sets").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSet)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionSet in the database
        List<QuestionSet> questionSetList = questionSetRepository.findAll();
        assertThat(questionSetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionSet() throws Exception {
        // Initialize the database
        questionSetService.save(questionSet);

        int databaseSizeBeforeDelete = questionSetRepository.findAll().size();

        // Delete the questionSet
        restQuestionSetMockMvc.perform(delete("/api/question-sets/{id}", questionSet.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionSet> questionSetList = questionSetRepository.findAll();
        assertThat(questionSetList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
