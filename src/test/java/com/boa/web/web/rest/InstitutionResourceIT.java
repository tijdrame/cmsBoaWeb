package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.Institution;
import com.boa.web.repository.InstitutionRepository;
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
 * Integration tests for the {@link InstitutionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InstitutionResourceIT {

  private static final String DEFAULT_INSTITUTION_ID = "AAAAAAAAAA";
  private static final String UPDATED_INSTITUTION_ID = "BBBBBBBBBB";

  private static final String DEFAULT_PAYS = "AAAAAAAAAA";
  private static final String UPDATED_PAYS = "BBBBBBBBBB";

  private static final String DEFAULT_CODE = "AAAAAAAAAA";
  private static final String UPDATED_CODE = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/institutions";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private InstitutionRepository institutionRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restInstitutionMockMvc;

  private Institution institution;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Institution createEntity(EntityManager em) {
    Institution institution = new Institution().institutionId(DEFAULT_INSTITUTION_ID).pays(DEFAULT_PAYS).code(DEFAULT_CODE);
    return institution;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Institution createUpdatedEntity(EntityManager em) {
    Institution institution = new Institution().institutionId(UPDATED_INSTITUTION_ID).pays(UPDATED_PAYS).code(UPDATED_CODE);
    return institution;
  }

  @BeforeEach
  public void initTest() {
    institution = createEntity(em);
  }

  @Test
  @Transactional
  void createInstitution() throws Exception {
    int databaseSizeBeforeCreate = institutionRepository.findAll().size();
    // Create the Institution
    restInstitutionMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
      .andExpect(status().isCreated());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeCreate + 1);
    Institution testInstitution = institutionList.get(institutionList.size() - 1);
    assertThat(testInstitution.getInstitutionId()).isEqualTo(DEFAULT_INSTITUTION_ID);
    assertThat(testInstitution.getPays()).isEqualTo(DEFAULT_PAYS);
    assertThat(testInstitution.getCode()).isEqualTo(DEFAULT_CODE);
  }

  @Test
  @Transactional
  void createInstitutionWithExistingId() throws Exception {
    // Create the Institution with an existing ID
    institution.setId(1L);

    int databaseSizeBeforeCreate = institutionRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restInstitutionMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
      .andExpect(status().isBadRequest());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllInstitutions() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    // Get all the institutionList
    restInstitutionMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(institution.getId().intValue())))
      .andExpect(jsonPath("$.[*].institutionId").value(hasItem(DEFAULT_INSTITUTION_ID)))
      .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
      .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
  }

  @Test
  @Transactional
  void getInstitution() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    // Get the institution
    restInstitutionMockMvc
      .perform(get(ENTITY_API_URL_ID, institution.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(institution.getId().intValue()))
      .andExpect(jsonPath("$.institutionId").value(DEFAULT_INSTITUTION_ID))
      .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
      .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
  }

  @Test
  @Transactional
  void getNonExistingInstitution() throws Exception {
    // Get the institution
    restInstitutionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewInstitution() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

    // Update the institution
    Institution updatedInstitution = institutionRepository.findById(institution.getId()).get();
    // Disconnect from session so that the updates on updatedInstitution are not directly saved in db
    em.detach(updatedInstitution);
    updatedInstitution.institutionId(UPDATED_INSTITUTION_ID).pays(UPDATED_PAYS).code(UPDATED_CODE);

    restInstitutionMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedInstitution.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedInstitution))
      )
      .andExpect(status().isOk());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    Institution testInstitution = institutionList.get(institutionList.size() - 1);
    assertThat(testInstitution.getInstitutionId()).isEqualTo(UPDATED_INSTITUTION_ID);
    assertThat(testInstitution.getPays()).isEqualTo(UPDATED_PAYS);
    assertThat(testInstitution.getCode()).isEqualTo(UPDATED_CODE);
  }

  @Test
  @Transactional
  void putNonExistingInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(
        put(ENTITY_API_URL_ID, institution.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(institution))
      )
      .andExpect(status().isBadRequest());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(institution))
      )
      .andExpect(status().isBadRequest());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(institution)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateInstitutionWithPatch() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

    // Update the institution using partial update
    Institution partialUpdatedInstitution = new Institution();
    partialUpdatedInstitution.setId(institution.getId());

    partialUpdatedInstitution.pays(UPDATED_PAYS).code(UPDATED_CODE);

    restInstitutionMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedInstitution.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstitution))
      )
      .andExpect(status().isOk());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    Institution testInstitution = institutionList.get(institutionList.size() - 1);
    assertThat(testInstitution.getInstitutionId()).isEqualTo(DEFAULT_INSTITUTION_ID);
    assertThat(testInstitution.getPays()).isEqualTo(UPDATED_PAYS);
    assertThat(testInstitution.getCode()).isEqualTo(UPDATED_CODE);
  }

  @Test
  @Transactional
  void fullUpdateInstitutionWithPatch() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();

    // Update the institution using partial update
    Institution partialUpdatedInstitution = new Institution();
    partialUpdatedInstitution.setId(institution.getId());

    partialUpdatedInstitution.institutionId(UPDATED_INSTITUTION_ID).pays(UPDATED_PAYS).code(UPDATED_CODE);

    restInstitutionMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedInstitution.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInstitution))
      )
      .andExpect(status().isOk());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
    Institution testInstitution = institutionList.get(institutionList.size() - 1);
    assertThat(testInstitution.getInstitutionId()).isEqualTo(UPDATED_INSTITUTION_ID);
    assertThat(testInstitution.getPays()).isEqualTo(UPDATED_PAYS);
    assertThat(testInstitution.getCode()).isEqualTo(UPDATED_CODE);
  }

  @Test
  @Transactional
  void patchNonExistingInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, institution.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(institution))
      )
      .andExpect(status().isBadRequest());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(institution))
      )
      .andExpect(status().isBadRequest());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamInstitution() throws Exception {
    int databaseSizeBeforeUpdate = institutionRepository.findAll().size();
    institution.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restInstitutionMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(institution)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Institution in the database
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteInstitution() throws Exception {
    // Initialize the database
    institutionRepository.saveAndFlush(institution);

    int databaseSizeBeforeDelete = institutionRepository.findAll().size();

    // Delete the institution
    restInstitutionMockMvc
      .perform(delete(ENTITY_API_URL_ID, institution.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Institution> institutionList = institutionRepository.findAll();
    assertThat(institutionList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
