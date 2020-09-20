package com.boa.web.service;

import com.boa.web.domain.Tracking;
import com.boa.web.repository.TrackingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Tracking}.
 */
@Service
@Transactional
public class TrackingService {

    private final Logger log = LoggerFactory.getLogger(TrackingService.class);

    private final TrackingRepository trackingRepository;

    public TrackingService(TrackingRepository trackingRepository) {
        this.trackingRepository = trackingRepository;
    }

    /**
     * Save a tracking.
     *
     * @param tracking the entity to save.
     * @return the persisted entity.
     */
    public Tracking save(Tracking tracking) {
        log.debug("Request to save Tracking : {}", tracking);
        return trackingRepository.save(tracking);
    }

    /**
     * Get all the trackings.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Tracking> findAll() {
        log.debug("Request to get all Trackings");
        return trackingRepository.findAll();
    }


    /**
     * Get one tracking by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tracking> findOne(Long id) {
        log.debug("Request to get Tracking : {}", id);
        return trackingRepository.findById(id);
    }

    /**
     * Delete the tracking by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tracking : {}", id);
        trackingRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<List<Tracking>> findByCriteira(String idClient, String serviceCalling) {
        log.debug("Request to get Tracking : {}", idClient);
        return trackingRepository.findByRequestTrContainsAndEndPointTrContainsOrderByIdDesc(idClient, serviceCalling);
    }
}
