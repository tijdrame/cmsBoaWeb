package com.boa.web.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.boa.web.IntegrationTest;
import com.boa.web.domain.Tracking;
import com.boa.web.repository.TrackingRepository;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link TrackingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrackingResourceIT {

  private static final String DEFAULT_TOKEN_TR = "AAAAAAAAAA";
  private static final String UPDATED_TOKEN_TR = "BBBBBBBBBB";

  private static final String DEFAULT_RESPONSE_TR = "AAAAAAAAAA";
  private static final String UPDATED_RESPONSE_TR = "BBBBBBBBBB";

  private static final String DEFAULT_CODE_RESPONSE = "AAAAAAAAAA";
  private static final String UPDATED_CODE_RESPONSE = "BBBBBBBBBB";

  private static final String DEFAULT_END_POINT_TR = "AAAAAAAAAA";
  private static final String UPDATED_END_POINT_TR = "BBBBBBBBBB";

  private static final Instant DEFAULT_DATE_REQUEST = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_DATE_REQUEST = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final Instant DEFAULT_DATE_RESPONSE = Instant.ofEpochMilli(0L);
  private static final Instant UPDATED_DATE_RESPONSE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

  private static final String DEFAULT_LOGIN_ACTEUR = "AAAAAAAAAA";
  private static final String UPDATED_LOGIN_ACTEUR = "BBBBBBBBBB";

  private static final String DEFAULT_REQUEST_TR = "AAAAAAAAAA";
  private static final String UPDATED_REQUEST_TR = "BBBBBBBBBB";

  private static final String ENTITY_API_URL = "/api/trackings";
  private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

  private static Random random = new Random();
  private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

  @Autowired
  private TrackingRepository trackingRepository;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restTrackingMockMvc;

  private Tracking tracking;

  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Tracking createEntity(EntityManager em) {
    Tracking tracking = new Tracking()
      .tokenTr(DEFAULT_TOKEN_TR)
      .responseTr(DEFAULT_RESPONSE_TR)
      .codeResponse(DEFAULT_CODE_RESPONSE)
      .endPointTr(DEFAULT_END_POINT_TR)
      .dateRequest(DEFAULT_DATE_REQUEST)
      .dateResponse(DEFAULT_DATE_RESPONSE)
      .loginActeur(DEFAULT_LOGIN_ACTEUR)
      .requestTr(DEFAULT_REQUEST_TR);
    return tracking;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Tracking createUpdatedEntity(EntityManager em) {
    Tracking tracking = new Tracking()
      .tokenTr(UPDATED_TOKEN_TR)
      .responseTr(UPDATED_RESPONSE_TR)
      .codeResponse(UPDATED_CODE_RESPONSE)
      .endPointTr(UPDATED_END_POINT_TR)
      .dateRequest(UPDATED_DATE_REQUEST)
      .dateResponse(UPDATED_DATE_RESPONSE)
      .loginActeur(UPDATED_LOGIN_ACTEUR)
      .requestTr(UPDATED_REQUEST_TR);
    return tracking;
  }

  @BeforeEach
  public void initTest() {
    tracking = createEntity(em);
  }

  @Test
  @Transactional
  void createTracking() throws Exception {
    int databaseSizeBeforeCreate = trackingRepository.findAll().size();
    // Create the Tracking
    restTrackingMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracking)))
      .andExpect(status().isCreated());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeCreate + 1);
    Tracking testTracking = trackingList.get(trackingList.size() - 1);
    assertThat(testTracking.getTokenTr()).isEqualTo(DEFAULT_TOKEN_TR);
    assertThat(testTracking.getResponseTr()).isEqualTo(DEFAULT_RESPONSE_TR);
    assertThat(testTracking.getCodeResponse()).isEqualTo(DEFAULT_CODE_RESPONSE);
    assertThat(testTracking.getEndPointTr()).isEqualTo(DEFAULT_END_POINT_TR);
    assertThat(testTracking.getDateRequest()).isEqualTo(DEFAULT_DATE_REQUEST);
    assertThat(testTracking.getDateResponse()).isEqualTo(DEFAULT_DATE_RESPONSE);
    assertThat(testTracking.getLoginActeur()).isEqualTo(DEFAULT_LOGIN_ACTEUR);
    assertThat(testTracking.getRequestTr()).isEqualTo(DEFAULT_REQUEST_TR);
  }

  @Test
  @Transactional
  void createTrackingWithExistingId() throws Exception {
    // Create the Tracking with an existing ID
    tracking.setId(1L);

    int databaseSizeBeforeCreate = trackingRepository.findAll().size();

    // An entity with an existing ID cannot be created, so this API call must fail
    restTrackingMockMvc
      .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracking)))
      .andExpect(status().isBadRequest());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeCreate);
  }

  @Test
  @Transactional
  void getAllTrackings() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    // Get all the trackingList
    restTrackingMockMvc
      .perform(get(ENTITY_API_URL + "?sort=id,desc"))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.[*].id").value(hasItem(tracking.getId().intValue())))
      .andExpect(jsonPath("$.[*].tokenTr").value(hasItem(DEFAULT_TOKEN_TR)))
      .andExpect(jsonPath("$.[*].responseTr").value(hasItem(DEFAULT_RESPONSE_TR)))
      .andExpect(jsonPath("$.[*].codeResponse").value(hasItem(DEFAULT_CODE_RESPONSE)))
      .andExpect(jsonPath("$.[*].endPointTr").value(hasItem(DEFAULT_END_POINT_TR)))
      .andExpect(jsonPath("$.[*].dateRequest").value(hasItem(DEFAULT_DATE_REQUEST.toString())))
      .andExpect(jsonPath("$.[*].dateResponse").value(hasItem(DEFAULT_DATE_RESPONSE.toString())))
      .andExpect(jsonPath("$.[*].loginActeur").value(hasItem(DEFAULT_LOGIN_ACTEUR)))
      .andExpect(jsonPath("$.[*].requestTr").value(hasItem(DEFAULT_REQUEST_TR.toString())));
  }

  @Test
  @Transactional
  void getTracking() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    // Get the tracking
    restTrackingMockMvc
      .perform(get(ENTITY_API_URL_ID, tracking.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(jsonPath("$.id").value(tracking.getId().intValue()))
      .andExpect(jsonPath("$.tokenTr").value(DEFAULT_TOKEN_TR))
      .andExpect(jsonPath("$.responseTr").value(DEFAULT_RESPONSE_TR))
      .andExpect(jsonPath("$.codeResponse").value(DEFAULT_CODE_RESPONSE))
      .andExpect(jsonPath("$.endPointTr").value(DEFAULT_END_POINT_TR))
      .andExpect(jsonPath("$.dateRequest").value(DEFAULT_DATE_REQUEST.toString()))
      .andExpect(jsonPath("$.dateResponse").value(DEFAULT_DATE_RESPONSE.toString()))
      .andExpect(jsonPath("$.loginActeur").value(DEFAULT_LOGIN_ACTEUR))
      .andExpect(jsonPath("$.requestTr").value(DEFAULT_REQUEST_TR.toString()));
  }

  @Test
  @Transactional
  void getNonExistingTracking() throws Exception {
    // Get the tracking
    restTrackingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  void putNewTracking() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();

    // Update the tracking
    Tracking updatedTracking = trackingRepository.findById(tracking.getId()).get();
    // Disconnect from session so that the updates on updatedTracking are not directly saved in db
    em.detach(updatedTracking);
    updatedTracking
      .tokenTr(UPDATED_TOKEN_TR)
      .responseTr(UPDATED_RESPONSE_TR)
      .codeResponse(UPDATED_CODE_RESPONSE)
      .endPointTr(UPDATED_END_POINT_TR)
      .dateRequest(UPDATED_DATE_REQUEST)
      .dateResponse(UPDATED_DATE_RESPONSE)
      .loginActeur(UPDATED_LOGIN_ACTEUR)
      .requestTr(UPDATED_REQUEST_TR);

    restTrackingMockMvc
      .perform(
        put(ENTITY_API_URL_ID, updatedTracking.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(updatedTracking))
      )
      .andExpect(status().isOk());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
    Tracking testTracking = trackingList.get(trackingList.size() - 1);
    assertThat(testTracking.getTokenTr()).isEqualTo(UPDATED_TOKEN_TR);
    assertThat(testTracking.getResponseTr()).isEqualTo(UPDATED_RESPONSE_TR);
    assertThat(testTracking.getCodeResponse()).isEqualTo(UPDATED_CODE_RESPONSE);
    assertThat(testTracking.getEndPointTr()).isEqualTo(UPDATED_END_POINT_TR);
    assertThat(testTracking.getDateRequest()).isEqualTo(UPDATED_DATE_REQUEST);
    assertThat(testTracking.getDateResponse()).isEqualTo(UPDATED_DATE_RESPONSE);
    assertThat(testTracking.getLoginActeur()).isEqualTo(UPDATED_LOGIN_ACTEUR);
    assertThat(testTracking.getRequestTr()).isEqualTo(UPDATED_REQUEST_TR);
  }

  @Test
  @Transactional
  void putNonExistingTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(
        put(ENTITY_API_URL_ID, tracking.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(tracking))
      )
      .andExpect(status().isBadRequest());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithIdMismatchTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(
        put(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(tracking))
      )
      .andExpect(status().isBadRequest());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void putWithMissingIdPathParamTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tracking)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void partialUpdateTrackingWithPatch() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();

    // Update the tracking using partial update
    Tracking partialUpdatedTracking = new Tracking();
    partialUpdatedTracking.setId(tracking.getId());

    partialUpdatedTracking.tokenTr(UPDATED_TOKEN_TR).codeResponse(UPDATED_CODE_RESPONSE).requestTr(UPDATED_REQUEST_TR);

    restTrackingMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTracking.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTracking))
      )
      .andExpect(status().isOk());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
    Tracking testTracking = trackingList.get(trackingList.size() - 1);
    assertThat(testTracking.getTokenTr()).isEqualTo(UPDATED_TOKEN_TR);
    assertThat(testTracking.getResponseTr()).isEqualTo(DEFAULT_RESPONSE_TR);
    assertThat(testTracking.getCodeResponse()).isEqualTo(UPDATED_CODE_RESPONSE);
    assertThat(testTracking.getEndPointTr()).isEqualTo(DEFAULT_END_POINT_TR);
    assertThat(testTracking.getDateRequest()).isEqualTo(DEFAULT_DATE_REQUEST);
    assertThat(testTracking.getDateResponse()).isEqualTo(DEFAULT_DATE_RESPONSE);
    assertThat(testTracking.getLoginActeur()).isEqualTo(DEFAULT_LOGIN_ACTEUR);
    assertThat(testTracking.getRequestTr()).isEqualTo(UPDATED_REQUEST_TR);
  }

  @Test
  @Transactional
  void fullUpdateTrackingWithPatch() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();

    // Update the tracking using partial update
    Tracking partialUpdatedTracking = new Tracking();
    partialUpdatedTracking.setId(tracking.getId());

    partialUpdatedTracking
      .tokenTr(UPDATED_TOKEN_TR)
      .responseTr(UPDATED_RESPONSE_TR)
      .codeResponse(UPDATED_CODE_RESPONSE)
      .endPointTr(UPDATED_END_POINT_TR)
      .dateRequest(UPDATED_DATE_REQUEST)
      .dateResponse(UPDATED_DATE_RESPONSE)
      .loginActeur(UPDATED_LOGIN_ACTEUR)
      .requestTr(UPDATED_REQUEST_TR);

    restTrackingMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, partialUpdatedTracking.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTracking))
      )
      .andExpect(status().isOk());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
    Tracking testTracking = trackingList.get(trackingList.size() - 1);
    assertThat(testTracking.getTokenTr()).isEqualTo(UPDATED_TOKEN_TR);
    assertThat(testTracking.getResponseTr()).isEqualTo(UPDATED_RESPONSE_TR);
    assertThat(testTracking.getCodeResponse()).isEqualTo(UPDATED_CODE_RESPONSE);
    assertThat(testTracking.getEndPointTr()).isEqualTo(UPDATED_END_POINT_TR);
    assertThat(testTracking.getDateRequest()).isEqualTo(UPDATED_DATE_REQUEST);
    assertThat(testTracking.getDateResponse()).isEqualTo(UPDATED_DATE_RESPONSE);
    assertThat(testTracking.getLoginActeur()).isEqualTo(UPDATED_LOGIN_ACTEUR);
    assertThat(testTracking.getRequestTr()).isEqualTo(UPDATED_REQUEST_TR);
  }

  @Test
  @Transactional
  void patchNonExistingTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, tracking.getId())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(tracking))
      )
      .andExpect(status().isBadRequest());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithIdMismatchTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(
        patch(ENTITY_API_URL_ID, count.incrementAndGet())
          .contentType("application/merge-patch+json")
          .content(TestUtil.convertObjectToJsonBytes(tracking))
      )
      .andExpect(status().isBadRequest());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void patchWithMissingIdPathParamTracking() throws Exception {
    int databaseSizeBeforeUpdate = trackingRepository.findAll().size();
    tracking.setId(count.incrementAndGet());

    // If url ID doesn't match entity ID, it will throw BadRequestAlertException
    restTrackingMockMvc
      .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tracking)))
      .andExpect(status().isMethodNotAllowed());

    // Validate the Tracking in the database
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  void deleteTracking() throws Exception {
    // Initialize the database
    trackingRepository.saveAndFlush(tracking);

    int databaseSizeBeforeDelete = trackingRepository.findAll().size();

    // Delete the tracking
    restTrackingMockMvc
      .perform(delete(ENTITY_API_URL_ID, tracking.getId()).accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<Tracking> trackingList = trackingRepository.findAll();
    assertThat(trackingList).hasSize(databaseSizeBeforeDelete - 1);
  }
}
