package org.infy.stg.web.rest;

import org.infy.stg.InterviewdbApp;
import org.infy.stg.config.TestSecurityConfiguration;
import org.infy.stg.domain.QTag;
import org.infy.stg.repository.QTagRepository;
import org.infy.stg.service.QTagService;

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
 * Integration tests for the {@link QTagResource} REST controller.
 */
@SpringBootTest(classes = { InterviewdbApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class QTagResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private QTagRepository qTagRepository;

    @Autowired
    private QTagService qTagService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQTagMockMvc;

    private QTag qTag;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QTag createEntity(EntityManager em) {
        QTag qTag = new QTag()
            .name(DEFAULT_NAME);
        return qTag;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QTag createUpdatedEntity(EntityManager em) {
        QTag qTag = new QTag()
            .name(UPDATED_NAME);
        return qTag;
    }

    @BeforeEach
    public void initTest() {
        qTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createQTag() throws Exception {
        int databaseSizeBeforeCreate = qTagRepository.findAll().size();
        // Create the QTag
        restQTagMockMvc.perform(post("/api/q-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qTag)))
            .andExpect(status().isCreated());

        // Validate the QTag in the database
        List<QTag> qTagList = qTagRepository.findAll();
        assertThat(qTagList).hasSize(databaseSizeBeforeCreate + 1);
        QTag testQTag = qTagList.get(qTagList.size() - 1);
        assertThat(testQTag.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createQTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = qTagRepository.findAll().size();

        // Create the QTag with an existing ID
        qTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQTagMockMvc.perform(post("/api/q-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qTag)))
            .andExpect(status().isBadRequest());

        // Validate the QTag in the database
        List<QTag> qTagList = qTagRepository.findAll();
        assertThat(qTagList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllQTags() throws Exception {
        // Initialize the database
        qTagRepository.saveAndFlush(qTag);

        // Get all the qTagList
        restQTagMockMvc.perform(get("/api/q-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(qTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getQTag() throws Exception {
        // Initialize the database
        qTagRepository.saveAndFlush(qTag);

        // Get the qTag
        restQTagMockMvc.perform(get("/api/q-tags/{id}", qTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(qTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingQTag() throws Exception {
        // Get the qTag
        restQTagMockMvc.perform(get("/api/q-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQTag() throws Exception {
        // Initialize the database
        qTagService.save(qTag);

        int databaseSizeBeforeUpdate = qTagRepository.findAll().size();

        // Update the qTag
        QTag updatedQTag = qTagRepository.findById(qTag.getId()).get();
        // Disconnect from session so that the updates on updatedQTag are not directly saved in db
        em.detach(updatedQTag);
        updatedQTag
            .name(UPDATED_NAME);

        restQTagMockMvc.perform(put("/api/q-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQTag)))
            .andExpect(status().isOk());

        // Validate the QTag in the database
        List<QTag> qTagList = qTagRepository.findAll();
        assertThat(qTagList).hasSize(databaseSizeBeforeUpdate);
        QTag testQTag = qTagList.get(qTagList.size() - 1);
        assertThat(testQTag.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingQTag() throws Exception {
        int databaseSizeBeforeUpdate = qTagRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQTagMockMvc.perform(put("/api/q-tags").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(qTag)))
            .andExpect(status().isBadRequest());

        // Validate the QTag in the database
        List<QTag> qTagList = qTagRepository.findAll();
        assertThat(qTagList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQTag() throws Exception {
        // Initialize the database
        qTagService.save(qTag);

        int databaseSizeBeforeDelete = qTagRepository.findAll().size();

        // Delete the qTag
        restQTagMockMvc.perform(delete("/api/q-tags/{id}", qTag.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QTag> qTagList = qTagRepository.findAll();
        assertThat(qTagList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
