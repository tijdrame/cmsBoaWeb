package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.CodeVisuel;
import com.boa.web.repository.CodeVisuelRepository;
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
 * Integration tests for the {@link CodeVisuelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CodeVisuelResourceIT {

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

  private static final String ENTITY_API_URL = "/api/code-visuels";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private CodeVisuelRepository codeVisuelRepository;

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
  void createCodeVisuel() throws Exception {
    int databaseSizeBeforeCreate = codeVisuelRepository.findAll().size();
    // Create the CodeVisuel
    restCodeVisuelMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
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
  void createCodeVisuelWithExistingId() throws Exception {
    // Create the CodeVisuel with an existing ID
    codeVisuel.setId(1L);

    int databaseSizeBeforeCreate = codeVisuelRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restCodeVisuelMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
      .andExpect(status().isBadRequest());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllCodeVisuels() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

    // Get all the codeVisuelList
    restCodeVisuelMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
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
  void getCodeVisuel() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

    // Get the codeVisuel
    restCodeVisuelMockMvc
      .perform(get(ENTITY_API_URL_ID, codeVisuel.getId()))
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
  void getNonExistingCodeVisuel() throws Exception {
    // Get the codeVisuel
    restCodeVisuelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewCodeVisuel() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

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

    restCodeVisuelMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedCodeVisuel.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedCodeVisuel))
      )
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
  void putNonExistingCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(
        put(ENTITY_API_URL_ID, codeVisuel.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(codeVisuel))
      )
      .andExpect(status().isBadRequest());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(codeVisuel))
      )
      .andExpect(status().isBadRequest());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateCodeVisuelWithPatch() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();

    // Update the codeVisuel using partial update
    CodeVisuel partialUpdatedCodeVisuel = new CodeVisuel();
    partialUpdatedCodeVisuel.setId(codeVisuel.getId());

    partialUpdatedCodeVisuel.code(UPDATED_CODE).rangeLow(UPDATED_RANGE_LOW);

    restCodeVisuelMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCodeVisuel.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeVisuel))
      )
      .andExpect(status().isOk());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
    CodeVisuel testCodeVisuel = codeVisuelList.get(codeVisuelList.size() - 1);
    assertThat(testCodeVisuel.getCode()).isEqualTo(UPDATED_CODE);
    assertThat(testCodeVisuel.getSousBin()).isEqualTo(DEFAULT_SOUS_BIN);
    assertThat(testCodeVisuel.getTaille()).isEqualTo(DEFAULT_TAILLE);
    assertThat(testCodeVisuel.getRangeLow()).isEqualTo(UPDATED_RANGE_LOW);
    assertThat(testCodeVisuel.getRangeHigh()).isEqualTo(DEFAULT_RANGE_HIGH);
  }

  @Test
  @Transactional
  void fullUpdateCodeVisuelWithPatch() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();

    // Update the codeVisuel using partial update
    CodeVisuel partialUpdatedCodeVisuel = new CodeVisuel();
    partialUpdatedCodeVisuel.setId(codeVisuel.getId());

    partialUpdatedCodeVisuel
      .code(UPDATED_CODE)
      .sousBin(UPDATED_SOUS_BIN)
      .taille(UPDATED_TAILLE)
      .rangeLow(UPDATED_RANGE_LOW)
      .rangeHigh(UPDATED_RANGE_HIGH);

    restCodeVisuelMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedCodeVisuel.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCodeVisuel))
      )
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
  void patchNonExistingCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, codeVisuel.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(codeVisuel))
      )
      .andExpect(status().isBadRequest());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(codeVisuel))
      )
      .andExpect(status().isBadRequest());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamCodeVisuel() throws Exception {
    int databaseSizeBeforeUpdate = codeVisuelRepository.findAll().size();
    codeVisuel.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restCodeVisuelMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(codeVisuel)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the CodeVisuel in the database
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteCodeVisuel() throws Exception {
    // Initialize the database
    codeVisuelRepository.saveAndFlush(codeVisuel);

    int databaseSizeBeforeDelete = codeVisuelRepository.findAll().size();

    // Delete the codeVisuel
    restCodeVisuelMockMvc
      .perform(delete(ENTITY_API_URL_ID, codeVisuel.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<CodeVisuel> codeVisuelList = codeVisuelRepository.findAll();
    assertThat(codeVisuelList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
