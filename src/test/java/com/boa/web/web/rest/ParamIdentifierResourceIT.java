package com.boa.web.web.rest;

import com.boa.web.CmsBoaWebApp;
import com.boa.web.domain.ParamIdentifier;
import com.boa.web.repository.ParamIdentifierRepository;
import com.boa.web.service.ParamIdentifierService;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ParamIdentifierResource} REST controller.
 */
@SpringBootTest(classes = CmsBoaWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ParamIdentifierResourceIT {

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ParamIdentifierRepository paramIdentifierRepository;

    @Autowired
    private ParamIdentifierService paramIdentifierService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restParamIdentifierMockMvc;

    private ParamIdentifier paramIdentifier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamIdentifier createEntity(EntityManager em) {
        ParamIdentifier paramIdentifier = new ParamIdentifier()
            .code(DEFAULT_CODE)
            .libelle(DEFAULT_LIBELLE);
        return paramIdentifier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamIdentifier createUpdatedEntity(EntityManager em) {
        ParamIdentifier paramIdentifier = new ParamIdentifier()
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);
        return paramIdentifier;
    }

    @BeforeEach
    public void initTest() {
        paramIdentifier = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamIdentifier() throws Exception {
        int databaseSizeBeforeCreate = paramIdentifierRepository.findAll().size();
        // Create the ParamIdentifier
        restParamIdentifierMockMvc.perform(post("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
            .andExpect(status().isCreated());

        // Validate the ParamIdentifier in the database
        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeCreate + 1);
        ParamIdentifier testParamIdentifier = paramIdentifierList.get(paramIdentifierList.size() - 1);
        assertThat(testParamIdentifier.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testParamIdentifier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
    }

    @Test
    @Transactional
    public void createParamIdentifierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramIdentifierRepository.findAll().size();

        // Create the ParamIdentifier with an existing ID
        paramIdentifier.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamIdentifierMockMvc.perform(post("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
            .andExpect(status().isBadRequest());

        // Validate the ParamIdentifier in the database
        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paramIdentifierRepository.findAll().size();
        // set the field null
        paramIdentifier.setCode(null);

        // Create the ParamIdentifier, which fails.


        restParamIdentifierMockMvc.perform(post("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
            .andExpect(status().isBadRequest());

        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLibelleIsRequired() throws Exception {
        int databaseSizeBeforeTest = paramIdentifierRepository.findAll().size();
        // set the field null
        paramIdentifier.setLibelle(null);

        // Create the ParamIdentifier, which fails.


        restParamIdentifierMockMvc.perform(post("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
            .andExpect(status().isBadRequest());

        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParamIdentifiers() throws Exception {
        // Initialize the database
        paramIdentifierRepository.saveAndFlush(paramIdentifier);

        // Get all the paramIdentifierList
        restParamIdentifierMockMvc.perform(get("/api/param-identifiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramIdentifier.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getParamIdentifier() throws Exception {
        // Initialize the database
        paramIdentifierRepository.saveAndFlush(paramIdentifier);

        // Get the paramIdentifier
        restParamIdentifierMockMvc.perform(get("/api/param-identifiers/{id}", paramIdentifier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paramIdentifier.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }
    @Test
    @Transactional
    public void getNonExistingParamIdentifier() throws Exception {
        // Get the paramIdentifier
        restParamIdentifierMockMvc.perform(get("/api/param-identifiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamIdentifier() throws Exception {
        // Initialize the database
        paramIdentifierService.save(paramIdentifier);

        int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();

        // Update the paramIdentifier
        ParamIdentifier updatedParamIdentifier = paramIdentifierRepository.findById(paramIdentifier.getId()).get();
        // Disconnect from session so that the updates on updatedParamIdentifier are not directly saved in db
        em.detach(updatedParamIdentifier);
        updatedParamIdentifier
            .code(UPDATED_CODE)
            .libelle(UPDATED_LIBELLE);

        restParamIdentifierMockMvc.perform(put("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParamIdentifier)))
            .andExpect(status().isOk());

        // Validate the ParamIdentifier in the database
        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
        ParamIdentifier testParamIdentifier = paramIdentifierList.get(paramIdentifierList.size() - 1);
        assertThat(testParamIdentifier.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testParamIdentifier.getLibelle()).isEqualTo(UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void updateNonExistingParamIdentifier() throws Exception {
        int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamIdentifierMockMvc.perform(put("/api/param-identifiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
            .andExpect(status().isBadRequest());

        // Validate the ParamIdentifier in the database
        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParamIdentifier() throws Exception {
        // Initialize the database
        paramIdentifierService.save(paramIdentifier);

        int databaseSizeBeforeDelete = paramIdentifierRepository.findAll().size();

        // Delete the paramIdentifier
        restParamIdentifierMockMvc.perform(delete("/api/param-identifiers/{id}", paramIdentifier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
        assertThat(paramIdentifierList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
