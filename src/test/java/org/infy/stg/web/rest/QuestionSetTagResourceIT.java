package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.QuestionSetTag;
import org.infy.stg.repository.QuestionSetTagRepository;
import org.infy.stg.service.QuestionSetTagService;

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
 * Integration tests for the {@link QuestionSetTagResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuestionSetTagResourceIT {

    private static final Integer DEFAULT_Q_COUNT = 1;
    private static final Integer UPDATED_Q_COUNT = 2;

    @Autowired
    private QuestionSetTagRepository questionSetTagRepository;

    @Autowired
    private QuestionSetTagService questionSetTagService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionSetTagMockMvc;

    private QuestionSetTag questionSetTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionSetTag createEntity(EntityManager em) {
        QuestionSetTag questionSetTag = new QuestionSetTag()
            .qCount(DEFAULT_Q_COUNT);
        return questionSetTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionSetTag createUpdatedEntity(EntityManager em) {
        QuestionSetTag questionSetTag = new QuestionSetTag()
            .qCount(UPDATED_Q_COUNT);
        return questionSetTag;
    }

    @BeforeEach
    public void initTest() {
        questionSetTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionSetTag() throws Exception {
        int databaseSizeBeforeCreate = questionSetTagRepository.findAll().size();
        // Create the QuestionSetTag
        restQuestionSetTagMockMvc.perform(post("/api/question-set-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSetTag)))
            .andExpect(status().isCreated());

        // Validate the QuestionSetTag in the database
        List<QuestionSetTag> questionSetTagList = questionSetTagRepository.findAll();
        assertThat(questionSetTagList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionSetTag testQuestionSetTag = questionSetTagList.get(questionSetTagList.size() - 1);
        assertThat(testQuestionSetTag.getqCount()).isEqualTo(DEFAULT_Q_COUNT);
    }

    @Test
    @Transactional
    public void createQuestionSetTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionSetTagRepository.findAll().size();

        // Create the QuestionSetTag with an existing ID
        questionSetTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionSetTagMockMvc.perform(post("/api/question-set-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSetTag)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionSetTag in the database
        List<QuestionSetTag> questionSetTagList = questionSetTagRepository.findAll();
        assertThat(questionSetTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionSetTags() throws Exception {
        // Initialize the database
        questionSetTagRepository.saveAndFlush(questionSetTag);

        // Get all the questionSetTagList
        restQuestionSetTagMockMvc.perform(get("/api/question-set-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionSetTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].qCount").value(hasItem(DEFAULT_Q_COUNT)));
    }
    
    @Test
    @Transactional
    public void getQuestionSetTag() throws Exception {
        // Initialize the database
        questionSetTagRepository.saveAndFlush(questionSetTag);

        // Get the questionSetTag
        restQuestionSetTagMockMvc.perform(get("/api/question-set-tags/{id}", questionSetTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionSetTag.getId().intValue()))
            .andExpect(jsonPath("$.qCount").value(DEFAULT_Q_COUNT));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionSetTag() throws Exception {
        // Get the questionSetTag
        restQuestionSetTagMockMvc.perform(get("/api/question-set-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionSetTag() throws Exception {
        // Initialize the database
        questionSetTagService.save(questionSetTag);

        int databaseSizeBeforeUpdate = questionSetTagRepository.findAll().size();

        // Update the questionSetTag
        QuestionSetTag updatedQuestionSetTag = questionSetTagRepository.findById(questionSetTag.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionSetTag are not directly saved in db
        em.detach(updatedQuestionSetTag);
        updatedQuestionSetTag
            .qCount(UPDATED_Q_COUNT);

        restQuestionSetTagMockMvc.perform(put("/api/question-set-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionSetTag)))
            .andExpect(status().isOk());

        // Validate the QuestionSetTag in the database
        List<QuestionSetTag> questionSetTagList = questionSetTagRepository.findAll();
        assertThat(questionSetTagList).hasSize(databaseSizeBeforeUpdate);
        QuestionSetTag testQuestionSetTag = questionSetTagList.get(questionSetTagList.size() - 1);
        assertThat(testQuestionSetTag.getqCount()).isEqualTo(UPDATED_Q_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionSetTag() throws Exception {
        int databaseSizeBeforeUpdate = questionSetTagRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionSetTagMockMvc.perform(put("/api/question-set-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionSetTag)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionSetTag in the database
        List<QuestionSetTag> questionSetTagList = questionSetTagRepository.findAll();
        assertThat(questionSetTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionSetTag() throws Exception {
        // Initialize the database
        questionSetTagService.save(questionSetTag);

        int databaseSizeBeforeDelete = questionSetTagRepository.findAll().size();

        // Delete the questionSetTag
        restQuestionSetTagMockMvc.perform(delete("/api/question-set-tags/{id}", questionSetTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionSetTag> questionSetTagList = questionSetTagRepository.findAll();
        assertThat(questionSetTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
