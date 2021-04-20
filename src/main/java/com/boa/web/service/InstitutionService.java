package com.boa.web.service;

import com.boa.web.domain.Institution;
import com.boa.web.repository.InstitutionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Institution}.
 */
@Service
@Transactional
public class InstitutionService {

  private final Logger log = LoggerFactory.getLogger(InstitutionService.class);

  private final InstitutionRepository institutionRepository;

  public InstitutionService(InstitutionRepository institutionRepository) {
    this.institutionRepository = institutionRepository;
  }

  /**
   * Save a institution.
   *
   * @param institution the entity to save.
   * @return the persisted entity.
   */
  public Institution save(Institution institution) {
    log.debug("Request to save Institution : {}", institution);
    return institutionRepository.save(institution);
  }

  /**
   * Partially update a institution.
   *
   * @param institution the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<Institution> partialUpdate(Institution institution) {
    log.debug("Request to partially update Institution : {}", institution);

    return institutionRepository
      .findById(institution.getId())
      .map(
        existingInstitution -> {
          if (institution.getInstitutionId() != null) {
            existingInstitution.setInstitutionId(institution.getInstitutionId());
          }
          if (institution.getPays() != null) {
            existingInstitution.setPays(institution.getPays());
          }
          if (institution.getCode() != null) {
            existingInstitution.setCode(institution.getCode());
          }

          return existingInstitution;
        }
      )
      .map(institutionRepository::save);
  }

  /**
   * Get all the institutions.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<Institution> findAll() {
    log.debug("Request to get all Institutions");
    return institutionRepository.findAll();
  }

  /**
   * Get one institution by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<Institution> findOne(Long id) {
    log.debug("Request to get Institution : {}", id);
    return institutionRepository.findById(id);
  }

  /**
   * Delete the institution by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete Institution : {}", id);
    institutionRepository.deleteById(id);
  }

  @Transactional(readOnly = true)
    public Map<String, String> findAllBis() {
        Map<String, String> theMap = new HashMap<>();
        log.debug("Request to get all ParamIdentifiers");
        List <Institution> list = institutionRepository.findAll();
        for (Institution it : list) {
            theMap.put(it.getInstitutionId(), it.getCode());
        }
        return theMap;
    }
}
