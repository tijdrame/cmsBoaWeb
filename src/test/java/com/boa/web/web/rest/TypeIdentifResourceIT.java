package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.TypeIdentif;
import com.boa.web.repository.TypeIdentifRepository;
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
 * Integration tests for the {@link TypeIdentifResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TypeIdentifResourceIT {

  private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
  private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

  private static final String DEFAULT_DEFAULT_IDENTIFIER = "AAAAAAAAAA";
  private static final String UPDATED_DEFAULT_IDENTIFIER = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/type-identifs";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private TypeIdentifRepository typeIdentifRepository;

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
    TypeIdentif typeIdentif = new TypeIdentif().identifier(DEFAULT_IDENTIFIER).defaultIdentifier(DEFAULT_DEFAULT_IDENTIFIER);
    return typeIdentif;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static TypeIdentif createUpdatedEntity(EntityManager em) {
    TypeIdentif typeIdentif = new TypeIdentif().identifier(UPDATED_IDENTIFIER).defaultIdentifier(UPDATED_DEFAULT_IDENTIFIER);
    return typeIdentif;
  }

  @BeforeEach
  public void initTest() {
    typeIdentif = createEntity(em);
  }

  @Test
  @Transactional
  void createTypeIdentif() throws Exception {
    int databaseSizeBeforeCreate = typeIdentifRepository.findAll().size();
    // Create the TypeIdentif
    restTypeIdentifMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
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
  void createTypeIdentifWithExistingId() throws Exception {
    // Create the TypeIdentif with an existing ID
    typeIdentif.setId(1L);

    int databaseSizeBeforeCreate = typeIdentifRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restTypeIdentifMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
      .andExpect(status().isBadRequest());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllTypeIdentifs() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    // Get all the typeIdentifList
    restTypeIdentifMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(typeIdentif.getId().intValue())))
      .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER)))
      .andExpect(jsonPath("$.[*].defaultIdentifier").value(hasItem(DEFAULT_DEFAULT_IDENTIFIER)));
  }

  @Test
  @Transactional
  void getTypeIdentif() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    // Get the typeIdentif
    restTypeIdentifMockMvc
      .perform(get(ENTITY_API_URL_ID, typeIdentif.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(typeIdentif.getId().intValue()))
      .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER))
      .andExpect(jsonPath("$.defaultIdentifier").value(DEFAULT_DEFAULT_IDENTIFIER));
  }

  @Test
  @Transactional
  void getNonExistingTypeIdentif() throws Exception {
    // Get the typeIdentif
    restTypeIdentifMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewTypeIdentif() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();

    // Update the typeIdentif
    TypeIdentif updatedTypeIdentif = typeIdentifRepository.findById(typeIdentif.getId()).get();
    // Disconnect from session so that the updates on updatedTypeIdentif are not directly saved in db
    em.detach(updatedTypeIdentif);
    updatedTypeIdentif.identifier(UPDATED_IDENTIFIER).defaultIdentifier(UPDATED_DEFAULT_IDENTIFIER);

    restTypeIdentifMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedTypeIdentif.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedTypeIdentif))
      )
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
  void putNonExistingTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(
        put(ENTITY_API_URL_ID, typeIdentif.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(typeIdentif))
      )
      .andExpect(status().isBadRequest());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(typeIdentif))
      )
      .andExpect(status().isBadRequest());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateTypeIdentifWithPatch() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();

    // Update the typeIdentif using partial update
    TypeIdentif partialUpdatedTypeIdentif = new TypeIdentif();
    partialUpdatedTypeIdentif.setId(typeIdentif.getId());

    restTypeIdentifMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentif.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentif))
      )
      .andExpect(status().isOk());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
    TypeIdentif testTypeIdentif = typeIdentifList.get(typeIdentifList.size() - 1);
    assertThat(testTypeIdentif.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    assertThat(testTypeIdentif.getDefaultIdentifier()).isEqualTo(DEFAULT_DEFAULT_IDENTIFIER);
  }

  @Test
  @Transactional
  void fullUpdateTypeIdentifWithPatch() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();

    // Update the typeIdentif using partial update
    TypeIdentif partialUpdatedTypeIdentif = new TypeIdentif();
    partialUpdatedTypeIdentif.setId(typeIdentif.getId());

    partialUpdatedTypeIdentif.identifier(UPDATED_IDENTIFIER).defaultIdentifier(UPDATED_DEFAULT_IDENTIFIER);

    restTypeIdentifMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTypeIdentif.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTypeIdentif))
      )
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
  void patchNonExistingTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, typeIdentif.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(typeIdentif))
      )
      .andExpect(status().isBadRequest());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(typeIdentif))
      )
      .andExpect(status().isBadRequest());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamTypeIdentif() throws Exception {
    int databaseSizeBeforeUpdate = typeIdentifRepository.findAll().size();
    typeIdentif.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTypeIdentifMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(typeIdentif)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the TypeIdentif in the database
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteTypeIdentif() throws Exception {
    // Initialize the database
    typeIdentifRepository.saveAndFlush(typeIdentif);

    int databaseSizeBeforeDelete = typeIdentifRepository.findAll().size();

    // Delete the typeIdentif
    restTypeIdentifMockMvc
      .perform(delete(ENTITY_API_URL_ID, typeIdentif.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<TypeIdentif> typeIdentifList = typeIdentifRepository.findAll();
    assertThat(typeIdentifList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
