package com.boa.web.web.rest;

import com.boa.web.domain.TypeIdentif;
import com.boa.web.service.TypeIdentifService;
import com.boa.web.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.boa.web.domain.TypeIdentif}.
 */
@RestController
@RequestMapping("/api")
public class TypeIdentifResource {

    private final Logger log = LoggerFactory.getLogger(TypeIdentifResource.class);

    private static final String ENTITY_NAME = "typeIdentif";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeIdentifService typeIdentifService;

    public TypeIdentifResource(TypeIdentifService typeIdentifService) {
        this.typeIdentifService = typeIdentifService;
    }

    /**
     * {@code POST  /type-identifs} : Create a new typeIdentif.
     *
     * @param typeIdentif the typeIdentif to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeIdentif, or with status {@code 400 (Bad Request)} if the typeIdentif has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-identifs")
    public ResponseEntity<TypeIdentif> createTypeIdentif(@RequestBody TypeIdentif typeIdentif) throws URISyntaxException {
        log.debug("REST request to save TypeIdentif : {}", typeIdentif);
        if (typeIdentif.getId() != null) {
            throw new BadRequestAlertException("A new typeIdentif cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeIdentif result = typeIdentifService.save(typeIdentif);
        return ResponseEntity.created(new URI("/api/type-identifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-identifs} : Updates an existing typeIdentif.
     *
     * @param typeIdentif the typeIdentif to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeIdentif,
     * or with status {@code 400 (Bad Request)} if the typeIdentif is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeIdentif couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-identifs")
    public ResponseEntity<TypeIdentif> updateTypeIdentif(@RequestBody TypeIdentif typeIdentif) throws URISyntaxException {
        log.debug("REST request to update TypeIdentif : {}", typeIdentif);
        if (typeIdentif.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeIdentif result = typeIdentifService.save(typeIdentif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, typeIdentif.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-identifs} : get all the typeIdentifs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeIdentifs in body.
     */
    @GetMapping("/type-identifs")
    public ResponseEntity<List<TypeIdentif>> getAllTypeIdentifs(Pageable pageable) {
        log.debug("REST request to get a page of TypeIdentifs");
        Page<TypeIdentif> page = typeIdentifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-identifs/:id} : get the "id" typeIdentif.
     *
     * @param id the id of the typeIdentif to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeIdentif, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-identifs/{id}")
    public ResponseEntity<TypeIdentif> getTypeIdentif(@PathVariable Long id) {
        log.debug("REST request to get TypeIdentif : {}", id);
        Optional<TypeIdentif> typeIdentif = typeIdentifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeIdentif);
    }

    /**
     * {@code DELETE  /type-identifs/:id} : delete the "id" typeIdentif.
     *
     * @param id the id of the typeIdentif to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-identifs/{id}")
    public ResponseEntity<Void> deleteTypeIdentif(@PathVariable Long id) {
        log.debug("REST request to delete TypeIdentif : {}", id);
        typeIdentifService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
