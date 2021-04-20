package com.boa.web.web.rest;

import com.boa.web.domain.ParamIdentifier;
import com.boa.web.repository.ParamIdentifierRepository;
import com.boa.web.service.ParamIdentifierService;
import com.boa.web.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.boa.web.domain.ParamIdentifier}.
 */
@RestController
@RequestMapping("/api")
public class ParamIdentifierResource {

  private final Logger log = LoggerFactory.getLogger(ParamIdentifierResource.class);

  private static final String ENTITY_NAME = "paramIdentifier";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final ParamIdentifierService paramIdentifierService;

  private final ParamIdentifierRepository paramIdentifierRepository;

  public ParamIdentifierResource(ParamIdentifierService paramIdentifierService, ParamIdentifierRepository paramIdentifierRepository) {
    this.paramIdentifierService = paramIdentifierService;
    this.paramIdentifierRepository = paramIdentifierRepository;
  }

  /**
   * {@code POST  /param-identifiers} : Create a new paramIdentifier.
   *
   * @param paramIdentifier the paramIdentifier to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramIdentifier, or with status {@code 400 (Bad Request)} if the paramIdentifier has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/param-identifiers")
  public ResponseEntity<ParamIdentifier> createParamIdentifier(@Valid @RequestBody ParamIdentifier paramIdentifier)
    throws URISyntaxException {
    log.debug("REST request to save ParamIdentifier : {}", paramIdentifier);
    if (paramIdentifier.getId() != null) {
      throw new BadRequestAlertException("A new paramIdentifier cannot already have an ID", ENTITY_NAME, "idexists");
    }
    ParamIdentifier result = paramIdentifierService.save(paramIdentifier);
    return ResponseEntity
      .created(new URI("/api/param-identifiers/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /param-identifiers/:id} : Updates an existing paramIdentifier.
   *
   * @param id the id of the paramIdentifier to save.
   * @param paramIdentifier the paramIdentifier to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramIdentifier,
   * or with status {@code 400 (Bad Request)} if the paramIdentifier is not valid,
   * or with status {@code 500 (Internal Server Error)} if the paramIdentifier couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/param-identifiers/{id}")
  public ResponseEntity<ParamIdentifier> updateParamIdentifier(
    @PathVariable(value = "id", required = false) final Long id,
    @Valid @RequestBody ParamIdentifier paramIdentifier
  ) throws URISyntaxException {
    log.debug("REST request to update ParamIdentifier : {}, {}", id, paramIdentifier);
    if (paramIdentifier.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, paramIdentifier.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!paramIdentifierRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    ParamIdentifier result = paramIdentifierService.save(paramIdentifier);
    return ResponseEntity
      .ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paramIdentifier.getId().toString()))
      .body(result);
  }

  /**
   * {@code PATCH  /param-identifiers/:id} : Partial updates given fields of an existing paramIdentifier, field will ignore if it is null
   *
   * @param id the id of the paramIdentifier to save.
   * @param paramIdentifier the paramIdentifier to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramIdentifier,
   * or with status {@code 400 (Bad Request)} if the paramIdentifier is not valid,
   * or with status {@code 404 (Not Found)} if the paramIdentifier is not found,
   * or with status {@code 500 (Internal Server Error)} if the paramIdentifier couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  /*@PatchMapping(value = "/param-identifiers/{id}", consumes = "application/merge-patch+json")
  public ResponseEntity<ParamIdentifier> partialUpdateParamIdentifier(
    @PathVariable(value = "id", required = false) final Long id,
    @NotNull @RequestBody ParamIdentifier paramIdentifier
  ) throws URISyntaxException {
    log.debug("REST request to partial update ParamIdentifier partially : {}, {}", id, paramIdentifier);
    if (paramIdentifier.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    if (!Objects.equals(id, paramIdentifier.getId())) {
      throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
    }

    if (!paramIdentifierRepository.existsById(id)) {
      throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
    }

    Optional<ParamIdentifier> result = paramIdentifierService.partialUpdate(paramIdentifier);

    return ResponseUtil.wrapOrNotFound(
      result,
      HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, paramIdentifier.getId().toString())
    );
  }*/
  /**
   * {@code GET  /param-identifiers} : get all the paramIdentifiers.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paramIdentifiers in body.
   */
  @GetMapping("/param-identifiers")
  public ResponseEntity<List<ParamIdentifier>> getAllParamIdentifiers(Pageable pageable) {
    log.debug("REST request to get a page of ParamIdentifiers");
    Page<ParamIdentifier> page = paramIdentifierService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /param-identifiers/:id} : get the "id" paramIdentifier.
   *
   * @param id the id of the paramIdentifier to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramIdentifier, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/param-identifiers/{id}")
  public ResponseEntity<ParamIdentifier> getParamIdentifier(@PathVariable Long id) {
    log.debug("REST request to get ParamIdentifier : {}", id);
    Optional<ParamIdentifier> paramIdentifier = paramIdentifierService.findOne(id);
    return ResponseUtil.wrapOrNotFound(paramIdentifier);
  }

  /**
   * {@code DELETE  /param-identifiers/:id} : delete the "id" paramIdentifier.
   *
   * @param id the id of the paramIdentifier to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/param-identifiers/{id}")
  public ResponseEntity<Void> deleteParamIdentifier(@PathVariable Long id) {
    log.debug("REST request to delete ParamIdentifier : {}", id);
    paramIdentifierService.delete(id);
    return ResponseEntity
      .noContent()
      .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
      .build();
  }
}
