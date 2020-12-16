package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.QuestionTag;
import org.infy.stg.repository.QuestionTagRepository;
import org.infy.stg.service.QuestionTagService;

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
 * Integration tests for the {@link QuestionTagResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QuestionTagResourceIT {

    @Autowired
    private QuestionTagRepository questionTagRepository;

    @Autowired
    private QuestionTagService questionTagService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionTagMockMvc;

    private QuestionTag questionTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionTag createEntity(EntityManager em) {
        QuestionTag questionTag = new QuestionTag();
        return questionTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionTag createUpdatedEntity(EntityManager em) {
        QuestionTag questionTag = new QuestionTag();
        return questionTag;
    }

    @BeforeEach
    public void initTest() {
        questionTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionTag() throws Exception {
        int databaseSizeBeforeCreate = questionTagRepository.findAll().size();
        // Create the QuestionTag
        restQuestionTagMockMvc.perform(post("/api/question-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTag)))
            .andExpect(status().isCreated());

        // Validate the QuestionTag in the database
        List<QuestionTag> questionTagList = questionTagRepository.findAll();
        assertThat(questionTagList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionTag testQuestionTag = questionTagList.get(questionTagList.size() - 1);
    }

    @Test
    @Transactional
    public void createQuestionTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionTagRepository.findAll().size();

        // Create the QuestionTag with an existing ID
        questionTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionTagMockMvc.perform(post("/api/question-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTag)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionTag in the database
        List<QuestionTag> questionTagList = questionTagRepository.findAll();
        assertThat(questionTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQuestionTags() throws Exception {
        // Initialize the database
        questionTagRepository.saveAndFlush(questionTag);

        // Get all the questionTagList
        restQuestionTagMockMvc.perform(get("/api/question-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionTag.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getQuestionTag() throws Exception {
        // Initialize the database
        questionTagRepository.saveAndFlush(questionTag);

        // Get the questionTag
        restQuestionTagMockMvc.perform(get("/api/question-tags/{id}", questionTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionTag.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionTag() throws Exception {
        // Get the questionTag
        restQuestionTagMockMvc.perform(get("/api/question-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionTag() throws Exception {
        // Initialize the database
        questionTagService.save(questionTag);

        int databaseSizeBeforeUpdate = questionTagRepository.findAll().size();

        // Update the questionTag
        QuestionTag updatedQuestionTag = questionTagRepository.findById(questionTag.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionTag are not directly saved in db
        em.detach(updatedQuestionTag);

        restQuestionTagMockMvc.perform(put("/api/question-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionTag)))
            .andExpect(status().isOk());

        // Validate the QuestionTag in the database
        List<QuestionTag> questionTagList = questionTagRepository.findAll();
        assertThat(questionTagList).hasSize(databaseSizeBeforeUpdate);
        QuestionTag testQuestionTag = questionTagList.get(questionTagList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionTag() throws Exception {
        int databaseSizeBeforeUpdate = questionTagRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionTagMockMvc.perform(put("/api/question-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTag)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionTag in the database
        List<QuestionTag> questionTagList = questionTagRepository.findAll();
        assertThat(questionTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionTag() throws Exception {
        // Initialize the database
        questionTagService.save(questionTag);

        int databaseSizeBeforeDelete = questionTagRepository.findAll().size();

        // Delete the questionTag
        restQuestionTagMockMvc.perform(delete("/api/question-tags/{id}", questionTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionTag> questionTagList = questionTagRepository.findAll();
        assertThat(questionTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
