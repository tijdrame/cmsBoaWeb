package com.boa.web.service;

import com.boa.web.domain.CodeVisuel;
import com.boa.web.repository.CodeVisuelRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CodeVisuel}.
 */
@Service
@Transactional
public class CodeVisuelService {

  private final Logger log = LoggerFactory.getLogger(CodeVisuelService.class);

  private final CodeVisuelRepository codeVisuelRepository;

  public CodeVisuelService(CodeVisuelRepository codeVisuelRepository) {
    this.codeVisuelRepository = codeVisuelRepository;
  }

  /**
   * Save a codeVisuel.
   *
   * @param codeVisuel the entity to save.
   * @return the persisted entity.
   */
  public CodeVisuel save(CodeVisuel codeVisuel) {
    log.debug("Request to save CodeVisuel : {}", codeVisuel);
    return codeVisuelRepository.save(codeVisuel);
  }

  /**
   * Partially update a codeVisuel.
   *
   * @param codeVisuel the entity to update partially.
   * @return the persisted entity.
   */
  public Optional<CodeVisuel> partialUpdate(CodeVisuel codeVisuel) {
    log.debug("Request to partially update CodeVisuel : {}", codeVisuel);

    return codeVisuelRepository
      .findById(codeVisuel.getId())
      .map(
        existingCodeVisuel -> {
          if (codeVisuel.getCode() != null) {
            existingCodeVisuel.setCode(codeVisuel.getCode());
          }
          if (codeVisuel.getSousBin() != null) {
            existingCodeVisuel.setSousBin(codeVisuel.getSousBin());
          }
          if (codeVisuel.getTaille() != null) {
            existingCodeVisuel.setTaille(codeVisuel.getTaille());
          }
          if (codeVisuel.getRangeLow() != null) {
            existingCodeVisuel.setRangeLow(codeVisuel.getRangeLow());
          }
          if (codeVisuel.getRangeHigh() != null) {
            existingCodeVisuel.setRangeHigh(codeVisuel.getRangeHigh());
          }

          return existingCodeVisuel;
        }
      )
      .map(codeVisuelRepository::save);
  }

  /**
   * Get all the codeVisuels.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<CodeVisuel> findAll() {
    log.debug("Request to get all CodeVisuels");
    return codeVisuelRepository.findAll();
  }

  /**
   * Get one codeVisuel by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<CodeVisuel> findOne(Long id) {
    log.debug("Request to get CodeVisuel : {}", id);
    return codeVisuelRepository.findById(id);
  }

  @Transactional(readOnly = true)
    public Optional<CodeVisuel> findBySearching(Long num) {
        log.debug("Request to get CodeVisuel : {}", num);
        return codeVisuelRepository.findBySearching(num);
    }

  /**
   * Delete the codeVisuel by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete CodeVisuel : {}", id);
    codeVisuelRepository.deleteById(id);
  }
}
