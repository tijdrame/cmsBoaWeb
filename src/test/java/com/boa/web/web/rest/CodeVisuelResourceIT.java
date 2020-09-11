package com.boa.web.web.rest;

import com.boa.web.CmsBoaWebApp;
import com.boa.web.domain.CodeVisuel;
import com.boa.web.repository.CodeVisuelRepository;
import com.boa.web.service.CodeVisuelService;

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
 * Integration tests for the {@link CodeVisuelResource} REST controller.
 */
@SpringBootTest(classes = CmsBoaWebApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CodeVisuelResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SOUS_BIN = "AAAAAAAAAA";
    private static final String UPDATED_SOUS_BIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAILLE = 1;
    private static final Integer UPDATED_TAILLE = 2;

    private static final Long DEFAULT_RANGE_LOW = 1L;
    private static final Long UPDATED_RANGE_LOW = 2L;

    private static final Long DEFAULT_RANGE_HIGH = 1L;
    private static final Long UPDATED_RANGE_HIGH = 2L;

    @Autowired
    private CodeVisuelRepository codeVisuelRepository;

    @Autowired
    private CodeVisuelService codeVisuelService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCodeVisuelMockMvc;

    private CodeVisuel codeVisuel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeVisuel createEntity(EntityManager em) {
        CodeVisuel codeVisuel = new CodeVisuel()
            .code(DEFAULT_CODE)
            .sousBin(DEFAULT_SOUS_BIN)
            .taille(DEFAULT_TAILLE)
            .rangeLow(DEFAULT_RANGE_LOW)
            .rangeHigh(DEFAULT_RANGE_HIGH);
        return codeVisuel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CodeVisuel createUpdatedEntity(EntityManager em) {
        CodeVisuel codeVisuel = new CodeVisuel()
            .code(UPDATED_CODE)
            .sousBin(UPDATED_SOUS_BIN)
            .taille(UPDATED_TAILLE)
            .rangeLow(UPDATED_RANGE_LOW)
            .rangeHigh(UPDATED_RANGE_HIGH);
        return codeVisuel;
    }

    @BeforeEach
    public void initTest() {
        codeVisuel = createEntity(em);
    }

    @Test
    @Transactional
    public void createCodeVisuel() throws Exception {
        int databaseSizeBeforeCreate = codeVisuelRepository.findAll().size();
        // Create the CodeVisuel
        restCodeVisuelMockMvc.perform(post("/api/code-visuels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
            .andExpect(status().isCreated());

        // Validate the CodeVisuel in the database
        List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
        assertThat(codeVisuelList).hasSize(databaseSizeBeforeCreate + 1);
        CodeVisuel testCodeVisuel = codeVisuelList.get(codeVisuelList.size() - 1);
        assertThat(testCodeVisuel.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCodeVisuel.getSousBin()).isEqualTo(DEFAULT_SOUS_BIN);
        assertThat(testCodeVisuel.getTaille()).isEqualTo(DEFAULT_TAILLE);
        assertThat(testCodeVisuel.getRangeLow()).isEqualTo(DEFAULT_RANGE_LOW);
        assertThat(testCodeVisuel.getRangeHigh()).isEqualTo(DEFAULT_RANGE_HIGH);
    }

    @Test
    @Transactional
    public void createCodeVisuelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = codeVisuelRepository.findAll().size();

        // Create the CodeVisuel with an existing ID
        codeVisuel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCodeVisuelMockMvc.perform(post("/api/code-visuels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
            .andExpect(status().isBadRequest());

        // Validate the CodeVisuel in the database
        List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
        assertThat(codeVisuelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCodeVisuels() throws Exception {
        // Initialize the database
        codeVisuelRepository.saveAndFlush(codeVisuel);

        // Get all the codeVisuelList
        restCodeVisuelMockMvc.perform(get("/api/code-visuels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(codeVisuel.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].sousBin").value(hasItem(DEFAULT_SOUS_BIN)))
            .andExpect(jsonPath("$.[*].taille").value(hasItem(DEFAULT_TAILLE)))
            .andExpect(jsonPath("$.[*].rangeLow").value(hasItem(DEFAULT_RANGE_LOW.intValue())))
            .andExpect(jsonPath("$.[*].rangeHigh").value(hasItem(DEFAULT_RANGE_HIGH.intValue())));
    }
    
    @Test
    @Transactional
    public void getCodeVisuel() throws Exception {
        // Initialize the database
        codeVisuelRepository.saveAndFlush(codeVisuel);

        // Get the codeVisuel
        restCodeVisuelMockMvc.perform(get("/api/code-visuels/{id}", codeVisuel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(codeVisuel.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.sousBin").value(DEFAULT_SOUS_BIN))
            .andExpect(jsonPath("$.taille").value(DEFAULT_TAILLE))
            .andExpect(jsonPath("$.rangeLow").value(DEFAULT_RANGE_LOW.intValue()))
            .andExpect(jsonPath("$.rangeHigh").value(DEFAULT_RANGE_HIGH.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCodeVisuel() throws Exception {
        // Get the codeVisuel
        restCodeVisuelMockMvc.perform(get("/api/code-visuels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCodeVisuel() throws Exception {
        // Initialize the database
        codeVisuelService.save(codeVisuel);

        int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();

        // Update the codeVisuel
        CodeVisuel updatedCodeVisuel = codeVisuelRepository.findById(codeVisuel.getId()).get();
        // Disconnect from session so that the updates on updatedCodeVisuel are not directly saved in db
        em.detach(updatedCodeVisuel);
        updatedCodeVisuel
            .code(UPDATED_CODE)
            .sousBin(UPDATED_SOUS_BIN)
            .taille(UPDATED_TAILLE)
            .rangeLow(UPDATED_RANGE_LOW)
            .rangeHigh(UPDATED_RANGE_HIGH);

        restCodeVisuelMockMvc.perform(put("/api/code-visuels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCodeVisuel)))
            .andExpect(status().isOk());

        // Validate the CodeVisuel in the database
        List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
        assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
        CodeVisuel testCodeVisuel = codeVisuelList.get(codeVisuelList.size() - 1);
        assertThat(testCodeVisuel.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCodeVisuel.getSousBin()).isEqualTo(UPDATED_SOUS_BIN);
        assertThat(testCodeVisuel.getTaille()).isEqualTo(UPDATED_TAILLE);
        assertThat(testCodeVisuel.getRangeLow()).isEqualTo(UPDATED_RANGE_LOW);
        assertThat(testCodeVisuel.getRangeHigh()).isEqualTo(UPDATED_RANGE_HIGH);
    }

    @Test
    @Transactional
    public void updateNonExistingCodeVisuel() throws Exception {
        int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCodeVisuelMockMvc.perform(put("/api/code-visuels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
            .andExpect(status().isBadRequest());

        // Validate the CodeVisuel in the database
        List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
        assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCodeVisuel() throws Exception {
        // Initialize the database
        codeVisuelService.save(codeVisuel);

        int databaseSizeBeforeDelete = codeVisuelRepository.findAll().size();

        // Delete the codeVisuel
        restCodeVisuelMockMvc.perform(delete("/api/code-visuels/{id}", codeVisuel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
        assertThat(codeVisuelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
