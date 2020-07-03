package com.boa.web.service;

import com.boa.web.domain.ParamIdentifier;
import com.boa.web.repository.ParamIdentifierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ParamIdentifier}.
 */
@Service
@Transactional
public class ParamIdentifierService {

    private final Logger log = LoggerFactory.getLogger(ParamIdentifierService.class);

    private final ParamIdentifierRepository paramIdentifierRepository;

    public ParamIdentifierService(ParamIdentifierRepository paramIdentifierRepository) {
        this.paramIdentifierRepository = paramIdentifierRepository;
    }

    /**
     * Save a paramIdentifier.
     *
     * @param paramIdentifier the entity to save.
     * @return the persisted entity.
     */
    public ParamIdentifier save(ParamIdentifier paramIdentifier) {
        log.debug("Request to save ParamIdentifier : {}", paramIdentifier);
        return paramIdentifierRepository.save(paramIdentifier);
    }

    /**
     * Get all the paramIdentifiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ParamIdentifier> findAll(Pageable pageable) {
        log.debug("Request to get all ParamIdentifiers");
        return paramIdentifierRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Map<Integer, String> findAll() {
        Map<Integer, String> theMap = new HashMap<>();
        log.debug("Request to get all ParamIdentifiers");
        List <ParamIdentifier> list = paramIdentifierRepository.findAll();
        for (ParamIdentifier it : list) {
            theMap.put(it.getCode(), it.getLibelle());
        }
        return theMap;
    }


    /**
     * Get one paramIdentifier by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ParamIdentifier> findOne(Long id) {
        log.debug("Request to get ParamIdentifier : {}", id);
        return paramIdentifierRepository.findById(id);
    }

    /**
     * Delete the paramIdentifier by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ParamIdentifier : {}", id);
        paramIdentifierRepository.deleteById(id);
    }
}
