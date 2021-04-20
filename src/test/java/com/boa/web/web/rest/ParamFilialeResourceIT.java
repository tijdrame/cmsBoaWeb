package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.ParamFiliale;
import com.boa.web.repository.ParamFilialeRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ParamFilialeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ParamFilialeResourceIT {

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

  private static final String ENTITY_API_URL = "/api/param-filiales";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private ParamFilialeRepository paramFilialeRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restParamFilialeMockMvc;

  private ParamFiliale paramFiliale;

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
  void createParamFiliale() throws Exception {
    int databaseSizeBeforeCreate = paramFilialeRepository.findAll().size();
    // Create the ParamFiliale
    restParamFilialeMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
      .andExpect(status().isCreated());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeCreate + 1);
    ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
    assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(DEFAULT_CODE_FILIALE);
    assertThat(testParamFiliale.getDesignationPays()).isEqualTo(DEFAULT_DESIGNATION_PAYS);
    assertThat(testParamFiliale.getEndPoint()).isEqualTo(DEFAULT_END_POINT);
    assertThat(testParamFiliale.getStatus()).isEqualTo(DEFAULT_STATUS);
    assertThat(testParamFiliale.getDateCre()).isEqualTo(DEFAULT_DATE_CRE);
    assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(DEFAULT_END_POINT_COMPTE);
  }

  @Test
  @Transactional
  void createParamFilialeWithExistingId() throws Exception {
    // Create the ParamFiliale with an existing ID
    paramFiliale.setId(1L);

    int databaseSizeBeforeCreate = paramFilialeRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restParamFilialeMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
      .andExpect(status().isBadRequest());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllParamFiliales() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

    // Get all the paramFilialeList
    restParamFilialeMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
  void getParamFiliale() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

    // Get the paramFiliale
    restParamFilialeMockMvc
      .perform(get(ENTITY_API_URL_ID, paramFiliale.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
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
  void getNonExistingParamFiliale() throws Exception {
    // Get the paramFiliale
    restParamFilialeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewParamFiliale() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

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

    restParamFilialeMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedParamFiliale.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedParamFiliale))
      )
      .andExpect(status().isOk());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
    ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
    assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(UPDATED_CODE_FILIALE);
    assertThat(testParamFiliale.getDesignationPays()).isEqualTo(UPDATED_DESIGNATION_PAYS);
    assertThat(testParamFiliale.getEndPoint()).isEqualTo(UPDATED_END_POINT);
    assertThat(testParamFiliale.getStatus()).isEqualTo(UPDATED_STATUS);
    assertThat(testParamFiliale.getDateCre()).isEqualTo(UPDATED_DATE_CRE);
    assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(UPDATED_END_POINT_COMPTE);
  }

  @Test
  @Transactional
  void putNonExistingParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(
        put(ENTITY_API_URL_ID, paramFiliale.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(paramFiliale))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(paramFiliale))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateParamFilialeWithPatch() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();

    // Update the paramFiliale using partial update
    ParamFiliale partialUpdatedParamFiliale = new ParamFiliale();
    partialUpdatedParamFiliale.setId(paramFiliale.getId());

    partialUpdatedParamFiliale.endPointCompte(UPDATED_END_POINT_COMPTE);

    restParamFilialeMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParamFiliale.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParamFiliale))
      )
      .andExpect(status().isOk());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
    ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
    assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(DEFAULT_CODE_FILIALE);
    assertThat(testParamFiliale.getDesignationPays()).isEqualTo(DEFAULT_DESIGNATION_PAYS);
    assertThat(testParamFiliale.getEndPoint()).isEqualTo(DEFAULT_END_POINT);
    assertThat(testParamFiliale.getStatus()).isEqualTo(DEFAULT_STATUS);
    assertThat(testParamFiliale.getDateCre()).isEqualTo(DEFAULT_DATE_CRE);
    assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(UPDATED_END_POINT_COMPTE);
  }

  @Test
  @Transactional
  void fullUpdateParamFilialeWithPatch() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();

    // Update the paramFiliale using partial update
    ParamFiliale partialUpdatedParamFiliale = new ParamFiliale();
    partialUpdatedParamFiliale.setId(paramFiliale.getId());

    partialUpdatedParamFiliale
      .codeFiliale(UPDATED_CODE_FILIALE)
      .designationPays(UPDATED_DESIGNATION_PAYS)
      .endPoint(UPDATED_END_POINT)
      .status(UPDATED_STATUS)
      .dateCre(UPDATED_DATE_CRE)
      .endPointCompte(UPDATED_END_POINT_COMPTE);

    restParamFilialeMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedParamFiliale.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedParamFiliale))
      )
      .andExpect(status().isOk());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
    ParamFiliale testParamFiliale = paramFilialeList.get(paramFilialeList.size() - 1);
    assertThat(testParamFiliale.getCodeFiliale()).isEqualTo(UPDATED_CODE_FILIALE);
    assertThat(testParamFiliale.getDesignationPays()).isEqualTo(UPDATED_DESIGNATION_PAYS);
    assertThat(testParamFiliale.getEndPoint()).isEqualTo(UPDATED_END_POINT);
    assertThat(testParamFiliale.getStatus()).isEqualTo(UPDATED_STATUS);
    assertThat(testParamFiliale.getDateCre()).isEqualTo(UPDATED_DATE_CRE);
    assertThat(testParamFiliale.getEndPointCompte()).isEqualTo(UPDATED_END_POINT_COMPTE);
  }

  @Test
  @Transactional
  void patchNonExistingParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, paramFiliale.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(paramFiliale))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(paramFiliale))
      )
      .andExpect(status().isBadRequest());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamParamFiliale() throws Exception {
    int databaseSizeBeforeUpdate = paramFilialeRepository.findAll().size();
    paramFiliale.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restParamFilialeMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paramFiliale)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the ParamFiliale in the database
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteParamFiliale() throws Exception {
    // Initialize the database
    paramFilialeRepository.saveAndFlush(paramFiliale);

    int databaseSizeBeforeDelete = paramFilialeRepository.findAll().size();

    // Delete the paramFiliale
    restParamFilialeMockMvc
      .perform(delete(ENTITY_API_URL_ID, paramFiliale.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<ParamFiliale> paramFilialeList = paramFilialeRepository.findAll();
    assertThat(paramFilialeList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
