package com.boa.web.web.rest;

import com.boa.web.CmsBoaWebApp;
import com.boa.web.domain.ParamFiliale;
import com.boa.web.repository.ParamFilialeRepository;
import com.boa.web.service.ParamFilialeService;
import com.boa.web.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.boa.web.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ParamFilialeResource} REST controller.
 */
@SpringBootTest(classes = CmsBoaWebApp.class)
public class ParamFilialeResourceIT {

    private static final String DEFAULT_CODE_FILIALE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FILIALE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_END_POINT = "AAAAAAAAAA";
    private static final String UPDATED_END_POINT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final Instant DEFAULT_DATE_CRE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_CRE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_END_POINT_COMPTE = "AAAAAAAAAA";
    private static final String UPDATED_END_POINT_COMPTE = "BBBBBBBBBB";

    @Autowired
    private ParamFilialeRepository paramFilialeRepository;

    @Autowired
    private ParamFilialeService paramFilialeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restParamFilialeMockMvc;

    private ParamFiliale paramFiliale;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ParamFilialeResource paramFilialeResource = new ParamFilialeResource(paramFilialeService);
        this.restParamFilialeMockMvc = MockMvcBuilders.standaloneSetup(paramFilialeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamFiliale createEntity(EntityManager em) {
        ParamFiliale paramFiliale = new ParamFiliale()
            .codeFiliale(DEFAULT_CODE_FILIALE)
            .designationPays(DEFAULT_DESIGNATION_PAYS)
            .endPoint(DEFAULT_END_POINT)
            .status(DEFAULT_STATUS)
            .dateCre(DEFAULT_DATE_CRE)
            .endPointCompte(DEFAULT_END_POINT_COMPTE);
        return paramFiliale;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ParamFiliale createUpdatedEntity(EntityManager em) {
        ParamFiliale paramFiliale = new ParamFiliale()
            .codeFiliale(UPDATED_CODE_FILIALE)
            .designationPays(UPDATED_DESIGNATION_PAYS)
            .endPoint(UPDATED_END_POINT)
            .status(UPDATED_STATUS)
            .dateCre(UPDATED_DATE_CRE)
            .endPointCompte(UPDATED_END_POINT_COMPTE);
        return paramFiliale;
    }

    @BeforeEach
    public void initTest() {
        paramFiliale = createEntity(em);
    }

    @Test
    @Transactional
    public void createParamFiliale() throws Exception {
        int databaseSizeBeforeCreate = paramFilialeRepository.findAll().size();

        // Create the ParamFiliale
        restParamFilialeMockMvc.perform(post("/api/param-filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
            .andExpect(status().isCreated());

        // Validate the ParamFiliale in the database
        List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
        assertThat(paramFilialeList).hasSize(databaseSizeBeforeCreate + 1);
        ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
        assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(DEFAULT_CODE_FILIALE);
        assertThat(testParamFiliale.getDesignationPays()).isEqualTo(DEFAULT_DESIGNATION_PAYS);
        assertThat(testParamFiliale.getEndPoint()).isEqualTo(DEFAULT_END_POINT);
        assertThat(testParamFiliale.isStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testParamFiliale.getDateCre()).isEqualTo(DEFAULT_DATE_CRE);
        assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(DEFAULT_END_POINT_COMPTE);
    }

    @Test
    @Transactional
    public void createParamFilialeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paramFilialeRepository.findAll().size();

        // Create the ParamFiliale with an existing ID
        paramFiliale.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParamFilialeMockMvc.perform(post("/api/param-filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
            .andExpect(status().isBadRequest());

        // Validate the ParamFiliale in the database
        List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
        assertThat(paramFilialeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParamFiliales() throws Exception {
        // Initialize the database
        paramFilialeRepository.saveAndFlush(paramFiliale);

        // Get all the paramFilialeList
        restParamFilialeMockMvc.perform(get("/api/param-filiales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paramFiliale.getId().intValue())))
            .andExpect(jsonPath("$.[*].codeFiliale").value(hasItem(DEFAULT_CODE_FILIALE)))
            .andExpect(jsonPath("$.[*].designationPays").value(hasItem(DEFAULT_DESIGNATION_PAYS)))
            .andExpect(jsonPath("$.[*].endPoint").value(hasItem(DEFAULT_END_POINT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].dateCre").value(hasItem(DEFAULT_DATE_CRE.toString())))
            .andExpect(jsonPath("$.[*].endPointCompte").value(hasItem(DEFAULT_END_POINT_COMPTE)));
    }
    
    @Test
    @Transactional
    public void getParamFiliale() throws Exception {
        // Initialize the database
        paramFilialeRepository.saveAndFlush(paramFiliale);

        // Get the paramFiliale
        restParamFilialeMockMvc.perform(get("/api/param-filiales/{id}", paramFiliale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paramFiliale.getId().intValue()))
            .andExpect(jsonPath("$.codeFiliale").value(DEFAULT_CODE_FILIALE))
            .andExpect(jsonPath("$.designationPays").value(DEFAULT_DESIGNATION_PAYS))
            .andExpect(jsonPath("$.endPoint").value(DEFAULT_END_POINT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()))
            .andExpect(jsonPath("$.dateCre").value(DEFAULT_DATE_CRE.toString()))
            .andExpect(jsonPath("$.endPointCompte").value(DEFAULT_END_POINT_COMPTE));
    }

    @Test
    @Transactional
    public void getNonExistingParamFiliale() throws Exception {
        // Get the paramFiliale
        restParamFilialeMockMvc.perform(get("/api/param-filiales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParamFiliale() throws Exception {
        // Initialize the database
        paramFilialeService.save(paramFiliale);

        int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();

        // Update the paramFiliale
        ParamFiliale updatedParamFiliale = paramFilialeRepository.findById(paramFiliale.getId()).get();
        // Disconnect from session so that the updates on updatedParamFiliale are not directly saved in db
        em.detach(updatedParamFiliale);
        updatedParamFiliale
            .codeFiliale(UPDATED_CODE_FILIALE)
            .designationPays(UPDATED_DESIGNATION_PAYS)
            .endPoint(UPDATED_END_POINT)
            .status(UPDATED_STATUS)
            .dateCre(UPDATED_DATE_CRE)
            .endPointCompte(UPDATED_END_POINT_COMPTE);

        restParamFilialeMockMvc.perform(put("/api/param-filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParamFiliale)))
            .andExpect(status().isOk());

        // Validate the ParamFiliale in the database
        List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
        assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
        ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
        assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(UPDATED_CODE_FILIALE);
        assertThat(testParamFiliale.getDesignationPays()).isEqualTo(UPDATED_DESIGNATION_PAYS);
        assertThat(testParamFiliale.getEndPoint()).isEqualTo(UPDATED_END_POINT);
        assertThat(testParamFiliale.isStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testParamFiliale.getDateCre()).isEqualTo(UPDATED_DATE_CRE);
        assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(UPDATED_END_POINT_COMPTE);
    }

    @Test
    @Transactional
    public void updateNonExistingParamFiliale() throws Exception {
        int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();

        // Create the ParamFiliale

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restParamFilialeMockMvc.perform(put("/api/param-filiales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
            .andExpect(status().isBadRequest());

        // Validate the ParamFiliale in the database
        List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
        assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParamFiliale() throws Exception {
        // Initialize the database
        paramFilialeService.save(paramFiliale);

        int databaseSizeBeforeDelete = paramFilialeRepository.findAll().size();

        // Delete the paramFiliale
        restParamFilialeMockMvc.perform(delete("/api/param-filiales/{id}", paramFiliale.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
        assertThat(paramFilialeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
