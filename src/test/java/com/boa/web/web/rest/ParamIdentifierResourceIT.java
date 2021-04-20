package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.ParamIdentifier;
import com.boa.web.repository.ParamIdentifierRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ParamIdentifierResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParamIdentifierResourceIT {

  private static final Integer DEFAULT_CODE = 1;
  private static final Integer UPDATED_CODE = 2;

  private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
  private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/param-identifiers";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ParamIdentifierRepository paramIdentifierRepository;

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
    ParamIdentifier paramIdentifier = new ParamIdentifier().code(DEFAULT_CODE).libelle(DEFAULT_LIBELLE);
    return paramIdentifier;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static ParamIdentifier createUpdatedEntity(EntityManager em) {
    ParamIdentifier paramIdentifier = new ParamIdentifier().code(UPDATED_CODE).libelle(UPDATED_LIBELLE);
    return paramIdentifier;
  }

  @BeforeEach
  public void initTest() {
    paramIdentifier = createEntity(em);
  }

  @Test
  @Transactional
  void createParamIdentifier() throws Exception {
    int databaseSizeBeforeCreate = paramIdentifierRepository.findAll().size();
    // Create the ParamIdentifier
    restParamIdentifierMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
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
  void createParamIdentifierWithExistingId() throws Exception {
    // Create the ParamIdentifier with an existing ID
    paramIdentifier.setId(1L);

    int databaseSizeBeforeCreate = paramIdentifierRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restParamIdentifierMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
      .andExpect(status().isBadRequest());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void checkCodeIsRequired() throws Exception {
    int databaseSizeBeforeTest = paramIdentifierRepository.findAll().size();
    // set the field null
    paramIdentifier.setCode(null);

    // Create the ParamIdentifier, which fails.

    restParamIdentifierMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
      .andExpect(status().isBadRequest());

    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void checkLibelleIsRequired() throws Exception {
    int databaseSizeBeforeTest = paramIdentifierRepository.findAll().size();
    // set the field null
    paramIdentifier.setLibelle(null);

    // Create the ParamIdentifier, which fails.

    restParamIdentifierMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
      .andExpect(status().isBadRequest());

    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeTest);
  }

  @Test
  @Transactional
  void getAllParamIdentifiers() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    // Get all the paramIdentifierList
    restParamIdentifierMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(paramIdentifier.getId().intValue())))
      .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
      .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
  }

  @Test
  @Transactional
  void getParamIdentifier() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    // Get the paramIdentifier
    restParamIdentifierMockMvc
      .perform(get(ENTITY_API_URL_ID, paramIdentifier.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(paramIdentifier.getId().intValue()))
      .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
      .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
  }

  @Test
  @Transactional
  void getNonExistingParamIdentifier() throws Exception {
    // Get the paramIdentifier
    restParamIdentifierMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewParamIdentifier() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();

    // Update the paramIdentifier
    ParamIdentifier updatedParamIdentifier = paramIdentifierRepository.findById(paramIdentifier.getId()).get();
    // Disconnect from session so that the updates on updatedParamIdentifier are not directly saved in db
    em.detach(updatedParamIdentifier);
    updatedParamIdentifier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

    restParamIdentifierMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedParamIdentifier.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedParamIdentifier))
      )
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
  void putNonExistingParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(
        put(ENTITY_API_URL_ID, paramIdentifier.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(paramIdentifier))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(paramIdentifier))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramIdentifier)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateParamIdentifierWithPatch() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();

    // Update the paramIdentifier using partial update
    ParamIdentifier partialUpdatedParamIdentifier = new ParamIdentifier();
    partialUpdatedParamIdentifier.setId(paramIdentifier.getId());

    partialUpdatedParamIdentifier.code(UPDATED_CODE);

    restParamIdentifierMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParamIdentifier.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParamIdentifier))
      )
      .andExpect(status().isOk());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
    ParamIdentifier testParamIdentifier = paramIdentifierList.get(paramIdentifierList.size() - 1);
    assertThat(testParamIdentifier.getCode()).isEqualTo(UPDATED_CODE);
    assertThat(testParamIdentifier.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
  }

  @Test
  @Transactional
  void fullUpdateParamIdentifierWithPatch() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();

    // Update the paramIdentifier using partial update
    ParamIdentifier partialUpdatedParamIdentifier = new ParamIdentifier();
    partialUpdatedParamIdentifier.setId(paramIdentifier.getId());

    partialUpdatedParamIdentifier.code(UPDATED_CODE).libelle(UPDATED_LIBELLE);

    restParamIdentifierMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParamIdentifier.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParamIdentifier))
      )
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
  void patchNonExistingParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, paramIdentifier.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(paramIdentifier))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(paramIdentifier))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamParamIdentifier() throws Exception {
    int databaseSizeBeforeUpdate = paramIdentifierRepository.findAll().size();
    paramIdentifier.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamIdentifierMockMvc
      .perform(
        patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paramIdentifier))
      )
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParamIdentifier in the database
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteParamIdentifier() throws Exception {
    // Initialize the database
    paramIdentifierRepository.saveAndFlush(paramIdentifier);

    int databaseSizeBeforeDelete = paramIdentifierRepository.findAll().size();

    // Delete the paramIdentifier
    restParamIdentifierMockMvc
      .perform(delete(ENTITY_API_URL_ID, paramIdentifier.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ParamIdentifier> paramIdentifierList = paramIdentifierRepository.findAll();
    assertThat(paramIdentifierList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
