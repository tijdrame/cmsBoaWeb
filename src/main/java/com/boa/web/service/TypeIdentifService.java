package com.boa.web.service;

import com.boa.web.domain.TypeIdentif;
import com.boa.web.repository.TypeIdentifRepository;
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
 * Service Implementation for managing {@link TypeIdentif}.
 */
@Service
@Transactional
public class TypeIdentifService {

    private final Logger log = LoggerFactory.getLogger(TypeIdentifService.class);

    private final TypeIdentifRepository typeIdentifRepository;

    public TypeIdentifService(TypeIdentifRepository typeIdentifRepository) {
        this.typeIdentifRepository = typeIdentifRepository;
    }

    /**
     * Save a typeIdentif.
     *
     * @param typeIdentif the entity to save.
     * @return the persisted entity.
     */
    public TypeIdentif save(TypeIdentif typeIdentif) {
        log.debug("Request to save TypeIdentif : {}", typeIdentif);
        return typeIdentifRepository.save(typeIdentif);
    }

    /**
     * Get all the typeIdentifs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeIdentif> findAll(Pageable pageable) {
        log.debug("Request to get all TypeIdentifs");
        return typeIdentifRepository.findAll(pageable);
    }


    /**
     * Get one typeIdentif by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeIdentif> findOne(Long id) {
        log.debug("Request to get TypeIdentif : {}", id);
        return typeIdentifRepository.findById(id);
    }

    /**
     * Delete the typeIdentif by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeIdentif : {}", id);
        typeIdentifRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Map<String, String> findAll() {
        Map<String, String> theMap = new HashMap<>();
        log.debug("Request to get all TypeIdentifiers");
        List <TypeIdentif> list = typeIdentifRepository.findAll();
        for (TypeIdentif it : list) {
            theMap.put(it.getIdentifier(), it.getDefaultIdentifier());
        }
        return theMap;
    }
}
