package com.boa.web.web.rest;

import com.boa.web.CmsBoaWebApp;
import com.boa.web.domain.TypeIdentif;
import com.boa.web.repository.TypeIdentifRepository;
import com.boa.web.service.TypeIdentifService;

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
 * Integration tests for the {@link TypeIdentifResource} REST controller.
 */
@SpringBootTest(classes = CmsBoaWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TypeIdentifResourceIT {

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private TypeIdentifRepository typeIdentifRepository;

    @Autowired
    private TypeIdentifService typeIdentifService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTypeIdentifMockMvc;

    private TypeIdentif typeIdentif;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentif createEntity(EntityManager em) {
        TypeIdentif typeIdentif = new TypeIdentif()
            .identifier(DEFAULT_IDENTIFIER)
            .defaultIdentifier(DEFAULT_DEFAULT_IDENTIFIER);
        return typeIdentif;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TypeIdentif createUpdatedEntity(EntityManager em) {
        TypeIdentif typeIdentif = new TypeIdentif()
            .identifier(UPDATED_IDENTIFIER)
            .defaultIdentifier(UPDATED_DEFAULT_IDENTIFIER);
        return typeIdentif;
    }

    @BeforeEach
    public void initTest() {
        typeIdentif = createEntity(em);
    }

    @Test
    @Transactional
    public void createTypeIdentif() throws Exception {
        int databaseSizeBeforeCreate = typeIdentifRepository.findAll().size();
        // Create the TypeIdentif
        restTypeIdentifMockMvc.perform(post("/api/type-identifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
            .andExpect(status().isCreated());

        // Validate the TypeIdentif in the database
        List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
        assertThat(typeIdentifList).hasSize(databaseSizeBeforeCreate + 1);
        TypeIdentif testTypeIdentif = typeIdentifList.get(typeIdentifList.size() - 1);
        assertThat(testTypeIdentif.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
        assertThat(testTypeIdentif.getDefaultIdentifier()).isEqualTo(DEFAULT_DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createTypeIdentifWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = typeIdentifRepository.findAll().size();

        // Create the TypeIdentif with an existing ID
        typeIdentif.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTypeIdentifMockMvc.perform(post("/api/type-identifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentif in the database
        List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
        assertThat(typeIdentifList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTypeIdentifs() throws Exception {
        // Initialize the database
        typeIdentifRepository.saveAndFlush(typeIdentif);

        // Get all the typeIdentifList
        restTypeIdentifMockMvc.perform(get("/api/type-identifs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(typeIdentif.getId().intValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].defaultIdentifier").value(hasItem(DEFAULT_DEFAULT_IDENTIFIER)));
    }
    
    @Test
    @Transactional
    public void getTypeIdentif() throws Exception {
        // Initialize the database
        typeIdentifRepository.saveAndFlush(typeIdentif);

        // Get the typeIdentif
        restTypeIdentifMockMvc.perform(get("/api/type-identifs/{id}", typeIdentif.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(typeIdentif.getId().intValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
            .andExpect(jsonPath("$.defaultIdentifier").value(DEFAULT_DEFAULT_IDENTIFIER));
    }
    @Test
    @Transactional
    public void getNonExistingTypeIdentif() throws Exception {
        // Get the typeIdentif
        restTypeIdentifMockMvc.perform(get("/api/type-identifs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTypeIdentif() throws Exception {
        // Initialize the database
        typeIdentifService.save(typeIdentif);

        int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();

        // Update the typeIdentif
        TypeIdentif updatedTypeIdentif = typeIdentifRepository.findById(typeIdentif.getId()).get();
        // Disconnect from session so that the updates on updatedTypeIdentif are not directly saved in db
        em.detach(updatedTypeIdentif);
        updatedTypeIdentif
            .identifier(UPDATED_IDENTIFIER)
            .defaultIdentifier(UPDATED_DEFAULT_IDENTIFIER);

        restTypeIdentifMockMvc.perform(put("/api/type-identifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTypeIdentif)))
            .andExpect(status().isOk());

        // Validate the TypeIdentif in the database
        List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
        assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
        TypeIdentif testTypeIdentif = typeIdentifList.get(typeIdentifList.size() - 1);
        assertThat(testTypeIdentif.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
        assertThat(testTypeIdentif.getDefaultIdentifier()).isEqualTo(UPDATED_DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingTypeIdentif() throws Exception {
        int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTypeIdentifMockMvc.perform(put("/api/type-identifs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
            .andExpect(status().isBadRequest());

        // Validate the TypeIdentif in the database
        List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
        assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTypeIdentif() throws Exception {
        // Initialize the database
        typeIdentifService.save(typeIdentif);

        int databaseSizeBeforeDelete = typeIdentifRepository.findAll().size();

        // Delete the typeIdentif
        restTypeIdentifMockMvc.perform(delete("/api/type-identifs/{id}", typeIdentif.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
        assertThat(typeIdentifList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
